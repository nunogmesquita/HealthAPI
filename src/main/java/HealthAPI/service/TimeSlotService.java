package HealthAPI.service;

import HealthAPI.dto.TimeSlotBookingRequest;
import HealthAPI.dto.TimeSlotResponse;
import HealthAPI.dto.TimeSlotStatusRequest;
import HealthAPI.model.TimeSlot;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public interface TimeSlotService {

    static boolean isSlotOverlap(List<LocalTime[]> timeSlots, TimeSlot timeSlot) {

        timeSlots.add(new LocalTime[]{
                getLocalTimeFromDate(timeSlot.getStartTime()),
                getLocalTimeFromDate(timeSlot.getEndTime())
        });

        timeSlots.sort(Comparator.comparing(time -> time[0]));

        for (int i = 1; i < timeSlots.size(); i++) {
            if (timeSlots.get(i - 1)[1].compareTo(timeSlots.get(i)[0]) > 0)
                return true;
        }
        return false;
    }

    static LocalTime getLocalTimeFromDate(Date date) {
        return date
                .toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalTime();
    }

    static LocalDate getLocalDate(Date date) {
        return date
                .toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();
    }

    ResponseEntity<?> createTimeSlot(TimeSlot timeSlot);

    List<TimeSlot> getAll();

    List<TimeSlot> getAllByHcpId(Long hcp);

    ResponseEntity<?> bookTimeSlot(TimeSlotBookingRequest timeSlotRequest);

    TimeSlot getAppointmentTimeSlotById(Long hcpId, Long timeSlotId);

    TimeSlotResponse getLiveTimeSlotStatus(TimeSlotStatusRequest timeSlotStatusRequest);

    TimeSlot deleteTimeSlotById(Long slotId);
}
