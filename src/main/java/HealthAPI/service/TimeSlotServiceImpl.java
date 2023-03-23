package HealthAPI.service;

import HealthAPI.dto.TimeSlotBookingRequest;
import HealthAPI.dto.TimeSlotResponse;
import HealthAPI.dto.TimeSlotStatusRequest;
import HealthAPI.model.Appointment;
import HealthAPI.model.TimeSlot;
import HealthAPI.repository.TimeSlotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    public static final long MIN_SLOT_DURATION = 900000;
    public static final long MAX_SLOT_DURATION = 1800000;

    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

    private static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");


    @Autowired
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private AppointmentService appointmentService;

    public ResponseEntity<?> createTimeSlot(TimeSlot timeSlot) {

        if (timeSlot.getStartTime().compareTo(timeSlot.getEndTime()) >= 0) {
            return ResponseEntity.badRequest().body("End Time must be greater than Start time");
        }

        long slotDuration = (timeSlot.getEndTime().getTime() - timeSlot.getStartTime().getTime());

        if (slotDuration < MIN_SLOT_DURATION || slotDuration > MAX_SLOT_DURATION) {
            return ResponseEntity.badRequest().body("Time duration must lies between 15 to 30 min");
        }

        Long currentHcpId = timeSlot.getHcpId();
        List<TimeSlot> allTimeSlotOfGivenHcp = timeSlotRepository.getAllByHcpId(currentHcpId);

        List<LocalTime[]> timeSlots = new ArrayList<>();

        for (TimeSlot currentTimeSlot : allTimeSlotOfGivenHcp) {
            LocalTime localStartTime = TimeSlotService.getLocalTimeFromDate(currentTimeSlot.getStartTime());
            LocalTime localEndTime = TimeSlotService.getLocalTimeFromDate(currentTimeSlot.getEndTime());
            timeSlots.add(new LocalTime[]{localStartTime, localEndTime});
        }

        if (TimeSlotService.isSlotOverlap(timeSlots, timeSlot)) {
            return ResponseEntity.badRequest().body("Error, Overlapping TimeSlot!");
        } else {
            timeSlotRepository.save(timeSlot);
            return ResponseEntity.ok().body(timeSlot);
        }
    }

    public List<TimeSlot> getAll() {
        return timeSlotRepository.findAll();
    }

    public List<TimeSlot> getAllByHcpId(Long hcp) {
        return timeSlotRepository.getAllByHcpId(hcp);
    }

    public ResponseEntity<?> bookTimeSlot(TimeSlotBookingRequest timeSlotRequest) {

        TimeSlot requestedTimeSlot = getAppointmentTimeSlotById(timeSlotRequest.getHcpId(), timeSlotRequest.getTimeSlotId());

        if (requestedTimeSlot == null) {
            return ResponseEntity.badRequest().body("Requested slot not found");
        }

        Long epochRequestedDate = TimeSlotService.getLocalDate(timeSlotRequest.getBookingDate()).toEpochDay();

        if (appointmentService.alreadyExist(timeSlotRequest, epochRequestedDate)) {
            return ResponseEntity.badRequest().body("Appointment Already Exist");
        }

        Appointment savedAppointment = appointmentService.createAppointment(timeSlotRequest);

        return ResponseEntity.ok(savedAppointment);
    }

    public TimeSlot getAppointmentTimeSlotById(Long hcpId, Long timeSlotId) {
        List<TimeSlot> timeSlots = timeSlotRepository.getAllByHcpId(hcpId);
        for (TimeSlot currentTimeSlot : timeSlots) {
            if (currentTimeSlot.getId().equals(timeSlotId)) {
                return currentTimeSlot;
            }
        }
        return null;
    }

    public TimeSlotResponse getLiveTimeSlotStatus(TimeSlotStatusRequest timeSlotStatusRequest) {

        Long hcpId = timeSlotStatusRequest.getHcpId();
        Long clientId = timeSlotStatusRequest.getClientId();
        Date currentDate = timeSlotStatusRequest.getCurrentDate();

        if (StringUtils.isEmpty(hcpId) || currentDate == null) return null;

        Long epochValueOfCurrDate = TimeSlotService.getLocalDate(currentDate).toEpochDay();

        List<TimeSlot> timeSlotList = timeSlotRepository.getAllByHcpId(hcpId);

        if (timeSlotList == null || timeSlotList.size() == 0) {
            return new TimeSlotResponse(null, "No slots found");
        } else {
            for (TimeSlot currentTimeSlot : timeSlotList) {
                List<Appointment> savedAppointment = appointmentService.findAppointmentByTimeSlot(currentTimeSlot, clientId);
                if (savedAppointment != null) {
                    for (Appointment appointment : savedAppointment) {
                        Long epochBookedDate = TimeSlotService.getLocalDate(appointment.getAppointmentDate()).toEpochDay();
                        if (epochBookedDate.equals(epochValueOfCurrDate)) {
                            currentTimeSlot.setBookedBySameClient(true);
                            break;
                        }
                    }
                }
                List<Appointment> savedByOther = appointmentService.findBookedAppointment(currentTimeSlot);
                if (savedByOther != null) {
                    for (Appointment appointment : savedByOther) {
                        Long epochBookedDate = TimeSlotService.getLocalDate(appointment.getAppointmentDate()).toEpochDay();
                        if (epochBookedDate.equals(epochValueOfCurrDate)) {
                            currentTimeSlot.setBooked(true);
                            break;
                        }
                    }
                }
            }
            return new TimeSlotResponse(timeSlotList, "Fetched");
        }
    }

    public TimeSlot deleteTimeSlotById(Long slotId) {
        TimeSlot deletedTimeSlot;

        if (timeSlotRepository.findById(slotId).isPresent()) {
            deletedTimeSlot = timeSlotRepository.findById(slotId).get();
        } else {
            return null;
        }

        timeSlotRepository.delete(deletedTimeSlot);
        return deletedTimeSlot;
    }
}
