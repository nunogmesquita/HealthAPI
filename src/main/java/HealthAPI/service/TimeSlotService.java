package HealthAPI.service;

import HealthAPI.converter.TimeSlotConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.TimeSlot.TimeSlotDto;
import HealthAPI.dto.TimeSlot.TimeSlotUpdateDto;
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
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentService appointmentService;
    private final UserConverter userConverter;
    private final TimeSlotConverter timeSlotConverter;

    @Autowired
    public TimeSlotService(TimeSlotRepository timeSlotRepository, AppointmentService appointmentService,
                           UserConverter userConverter, TimeSlotConverter timeSlotConverter) {
        this.timeSlotRepository = timeSlotRepository;
        this.appointmentService = appointmentService;
        this.userConverter = userConverter;
        this.timeSlotConverter = timeSlotConverter;
    }

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

    public List<TimeSlotDto> getAvailableTimeSlots(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<TimeSlot> timeSlots = timeSlotRepository.findByIsBookedFalse(pageable).getContent();
        return timeSlots.parallelStream()
                .map(timeSlotConverter::fromTimeSlotToTimeSlotDto)
                .toList();
    }

    public List<TimeSlotDto> getAvailableTimeSlotsByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<TimeSlot> timeSlots = timeSlotRepository.findByUserAndIsBookedFalse(userId, pageable).getContent();
        return timeSlots.parallelStream()
                .map(timeSlotConverter::fromTimeSlotToTimeSlotDto)
                .toList();
    }

    public List<TimeSlotDto> getAvailableTimeSlotsBySpeciality(Speciality speciality, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<TimeSlot> timeSlots = timeSlotRepository.findByUser_SpecialityAndIsBookedFalse(speciality, pageable).getContent();
        return timeSlots.parallelStream()
                .map(timeSlotConverter::fromTimeSlotToTimeSlotDto)
                .toList();

    }

    public void deleteAllTimeSlotsByUser(Long userId) {
        timeSlotRepository.deleteByUser_Id(userId);
    }

    public void deleteTimeSlot(Long id) {
        timeSlotRepository.deleteById(id);
    }

    public TimeSlotDto updateTimeSlot(Long id, TimeSlotUpdateDto updatedTimeSlot) {
        TimeSlot existingTimeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", id));

        existingTimeSlot.setStartTime(updatedTimeSlot.getTime());
        existingTimeSlot.setDayOfWeek(updatedTimeSlot.getDayOfWeek());
        existingTimeSlot.setMonth(updatedTimeSlot.getMonth());
        existingTimeSlot.setYear(updatedTimeSlot.getYear());
        timeSlotRepository.save(existingTimeSlot);
        return timeSlotConverter.fromTimeSlotToTimeSlotDto(existingTimeSlot);
    }

    public TimeSlot getTimeSlotById(Long id) {
        return timeSlotRepository.findById(id)
                .orElseThrow();
    }

}