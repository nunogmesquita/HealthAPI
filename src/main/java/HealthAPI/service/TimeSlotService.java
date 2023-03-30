package HealthAPI.service;

import HealthAPI.converter.TimeSlotConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.timeSlot.TimeSlotDto;
import HealthAPI.dto.timeSlot.TimeSlotUpdateDto;
import HealthAPI.dto.timeSlot.WeeklyTimeSlotDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.exception.ResourceNotFoundException;
import HealthAPI.exception.TimeSlotNotFound;
import HealthAPI.model.*;
import HealthAPI.repository.TimeSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotConverter timeSlotConverter;
    private final UserService userService;
    private final UserConverter userConverter;

    public void generateWeeklyTimeSlots(WeeklyTimeSlotDto weeklyTimeSlotDto) {
        UserDto userDto = userService.getUserById(weeklyTimeSlotDto.getUserId());
        User user = userConverter.fromUserDtoToUser(userDto);
        LocalDate startDate = weeklyTimeSlotDto.getDate();
        LocalDate endDate = startDate.plusMonths(1);
        List<LocalTimeRange> excludedRanges = new ArrayList<>();
        if (weeklyTimeSlotDto.getExcludedTimeRanges() != null) {
            for (String range : weeklyTimeSlotDto.getExcludedTimeRanges()) {
                LocalTimeRange timeRange = LocalTimeRange.parse(range);
                excludedRanges.add(timeRange);
            }
        }
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (DayOfWeek dayOfWeek : weeklyTimeSlotDto.getDayOfWeeks()) {
            LocalDate currentDate = startDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
            while (currentDate.isBefore(endDate)) {
                LocalDateTime currentDateTime = LocalDateTime.of(currentDate, weeklyTimeSlotDto.getInitialHour());
                while (currentDateTime.isBefore(LocalDateTime.of(currentDate, weeklyTimeSlotDto.getFinishingHour()))) {
                    boolean excluded = false;
                    for (LocalTimeRange range : excludedRanges) {
                        if (range.getStart().equals(currentDateTime.toLocalTime())) {
                            excluded = true;
                            break;
                        }
                    }
                    if (!excluded) {
                        timeSlots.add(TimeSlot.builder()
                                .startTime(currentDateTime)
                                .dayOfWeek(dayOfWeek.toString())
                                .user(user)
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

    public void deleteTimeSlot(Long timeSlotId) {
        timeSlotRepository.deleteById(timeSlotId);
    }

    public TimeSlotDto updateTimeSlot(Long timeSlotId, TimeSlotUpdateDto updatedTimeSlot) {
        TimeSlot existingTimeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", "id", timeSlotId));
        existingTimeSlot.setStartTime(updatedTimeSlot.getTime());
        existingTimeSlot.setDayOfWeek(updatedTimeSlot.getTime().getDayOfWeek().toString());
        timeSlotRepository.save(existingTimeSlot);
        return timeSlotConverter.fromTimeSlotToTimeSlotDto(existingTimeSlot);
    }

    public List<TimeSlotDto> getAvailableTimeSlots(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<TimeSlot> timeSlots = timeSlotRepository.findByIsBookedFalse(pageable).getContent();
        return timeSlots.parallelStream()
                .map(timeSlotConverter::fromTimeSlotToTimeSlotDto)
                .toList();
    }

    public List<TimeSlotDto> getAvailableTimeSlotsByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").ascending());
        List<TimeSlot> timeSlots = timeSlotRepository.findByUser_IdAndIsBookedFalse(userId, pageable).getContent();
        return timeSlots.parallelStream()
                .map(timeSlotConverter::fromTimeSlotToTimeSlotDto)
                .toList();
    }

    public List<TimeSlotDto> getAvailableTimeSlotsBySpeciality(Speciality speciality, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").ascending());
        List<TimeSlot> timeSlots = timeSlotRepository.findByUser_SpecialityAndIsBookedFalse(speciality, pageable).getContent();
        return timeSlots.parallelStream()
                .map(timeSlotConverter::fromTimeSlotToTimeSlotDto)
                .toList();
    }

    @Transactional
    public void deleteAllTimeSlotsByUser(Long userId) {
        timeSlotRepository.deleteAllByUser_Id(userId);
    }

    public TimeSlot getTimeSlotById(Long timeSlotId) {
        return timeSlotRepository.findById(timeSlotId)
                .orElseThrow(TimeSlotNotFound::new);
    }

}