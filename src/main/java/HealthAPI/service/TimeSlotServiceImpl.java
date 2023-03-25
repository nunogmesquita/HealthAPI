package HealthAPI.service;

import HealthAPI.converter.TimeSlotConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto;
import HealthAPI.dto.User.UserDto;
import HealthAPI.exceptions.ResourceNotFoundException;
import HealthAPI.model.*;
import HealthAPI.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentService appointmentService;
    private final UserConverter userConverter;
    private final TimeSlotConverter timeSlotConverter;

    @Autowired
    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository, AppointmentService appointmentService,
                               UserConverter userConverter, TimeSlotConverter timeSlotConverter) {
        this.timeSlotRepository = timeSlotRepository;
        this.appointmentService = appointmentService;
        this.userConverter = userConverter;
        this.timeSlotConverter = timeSlotConverter;
    }

    @Override
    public void generateWeeklyTimeSlots(WeeklyTimeSlotDto weeklyTimeSlotDto, UserDto userDto) {
        LocalDateTime startDateTime = LocalDateTime.of(weeklyTimeSlotDto.getDate(), LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(weeklyTimeSlotDto.getDate(), LocalTime.MAX);
        List<LocalTimeRange> excludedRanges = new ArrayList<>();
        if (weeklyTimeSlotDto.getExcludedTimeRanges() != null) {
            for (String range : weeklyTimeSlotDto.getExcludedTimeRanges()) {
                LocalTimeRange timeRange = LocalTimeRange.parse(range);
                excludedRanges.add(timeRange);
            }
        }
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (DayOfWeek dayOfWeek : weeklyTimeSlotDto.getDayOfWeeks()) {
            LocalDate currentDate = startDateTime.toLocalDate().with(TemporalAdjusters.nextOrSame(dayOfWeek));
            while (currentDate.isBefore(endDateTime.toLocalDate())) {
                LocalDateTime currentDateTime = LocalDateTime.of(currentDate, weeklyTimeSlotDto.getInitialHour());
                while (currentDateTime.plus(Duration.ofHours(1)).isBefore(LocalDateTime.of(currentDate, weeklyTimeSlotDto.getFinishingHour()))) {
                    boolean excluded = false;
                    for (LocalTimeRange range : excludedRanges) {
                        if (range.contains(currentDateTime.toLocalTime())) {
                            excluded = true;
                            break;
                        }
                    }
                    if (!excluded) {
                        timeSlots.add(TimeSlot.builder()
                                .startTime(currentDateTime)
                                .endTime(currentDateTime.plus(Duration.ofHours(1)))
                                .dayOfWeek(dayOfWeek)
                                .month(currentDate.getMonthValue())
                                .year(currentDate.getYear())
                                .user(userConverter.fromUserDtoToUser(userDto))
                                .build());
                    }
                    currentDateTime = currentDateTime.plus(Duration.ofHours(1));
                }
                currentDate = currentDate.plusWeeks(1);
            }
        }
        for (TimeSlot timeSlot : timeSlots) {
            timeSlotRepository.save(timeSlot);
        }
    }

    public List<TimeSlot> getAvailableTimeSlots(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return timeSlotRepository.findByIsBookedFalse(pageable).getContent();
    }

    public List<TimeSlot> getAvailableTimeSlotsByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return timeSlotRepository.findByUserAndIsBookedFalse(userId, pageable).getContent();
    }

    public List<TimeSlot> getAvailableTimeSlotsBySpeciality(Speciality speciality, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return timeSlotRepository.findByUser_SpecialityAndIsBookedFalse(speciality, pageable).getContent();
    }

    public void deleteAllTimeSlotsByUser(Long userId) {
        timeSlotRepository.deleteByUser_Id(userId);
    }

    public void deleteTimeSlot(Long id) {
        timeSlotRepository.deleteById(id);
    }

    public void updateTimeSlot(Long id, TimeSlot updatedTimeSlot) {
        TimeSlot existingTimeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", id));

        existingTimeSlot.setStartTime(updatedTimeSlot.getStartTime());
        existingTimeSlot.setEndTime(updatedTimeSlot.getEndTime());
        existingTimeSlot.setDayOfWeek(updatedTimeSlot.getDayOfWeek());
        existingTimeSlot.setMonth(updatedTimeSlot.getMonth());
        existingTimeSlot.setYear(updatedTimeSlot.getYear());
        existingTimeSlot.setUser(updatedTimeSlot.getUser());
        existingTimeSlot.setAppointment(updatedTimeSlot.getAppointment());
        existingTimeSlot.setBooked(updatedTimeSlot.isBooked());

        timeSlotRepository.save(existingTimeSlot);
    }

    public TimeSlot getTimeSlotById(Long id) {
        return timeSlotRepository.findById(id)
                .orElseThrow();
    }

}