package HealthAPI.service;

import HealthAPI.converter.TimeSlotConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.TimeSlot.TimeSlotDto;
import HealthAPI.dto.TimeSlot.TimeSlotUpdateDto;
import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto;
import HealthAPI.exception.ResourceNotFoundException;
import HealthAPI.model.*;
import HealthAPI.repository.TimeSlotRepository;
import HealthAPI.repository.UserRepository;
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
    private final UserConverter userConverter;
    private final TimeSlotConverter timeSlotConverter;
    private final UserRepository userRepository;

    @Autowired
    public TimeSlotService(TimeSlotRepository timeSlotRepository, UserConverter userConverter,
                           TimeSlotConverter timeSlotConverter, UserRepository userRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.userConverter = userConverter;
        this.timeSlotConverter = timeSlotConverter;
        this.userRepository = userRepository;
    }

    public void generateWeeklyTimeSlots(WeeklyTimeSlotDto weeklyTimeSlotDto) {
        User user = userRepository.findById(weeklyTimeSlotDto.getUserId()).orElseThrow();
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
                        if(range.getStart().equals(currentDateTime.toLocalTime())){
                            excluded = true;
                            break;
                        }
//                        if (range.contains(currentDateTime.toLocalTime())) {
//                            excluded = true;
//                            break;
//                        }
                    }
                    if (!excluded) {
                        timeSlots.add(TimeSlot.builder()
                                .startTime(currentDateTime)
                                .dayOfWeek(dayOfWeek)
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
        timeSlotRepository.save(existingTimeSlot);
        return timeSlotConverter.fromTimeSlotToTimeSlotDto(existingTimeSlot);
    }

    public TimeSlot getTimeSlotById(Long id) {
        return timeSlotRepository.findById(id)
                .orElseThrow();
    }

}