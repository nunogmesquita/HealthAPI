package HealthAPI.controller;

import HealthAPI.dto.timeSlot.TimeSlotDto;
import HealthAPI.dto.timeSlot.TimeSlotUpdateDto;
import HealthAPI.dto.timeSlot.WeeklyTimeSlotDto;
import HealthAPI.messages.Responses;
import HealthAPI.model.Speciality;
import HealthAPI.service.TimeSlotServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotServiceImpl timeSlotService;

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String generateWeeklyTimeSlots(@Valid @RequestBody WeeklyTimeSlotDto weeklyTimeSlotDto) {
        timeSlotService.generateWeeklyTimeSlots(weeklyTimeSlotDto);
        return Responses.TIMESLOTS_CREATED;
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @DeleteMapping("/{timeSlotId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTimeSlot(@PathVariable Long timeSlotId) {
        timeSlotService.deleteTimeSlot(timeSlotId);
        return Responses.DELETED_TIMESLOT.formatted(timeSlotId);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @PatchMapping("/{timeSlotId}")
    @ResponseStatus(HttpStatus.OK)
    public TimeSlotDto updateTimeSlot(@PathVariable Long timeSlotId,
                                      @Valid @RequestBody TimeSlotUpdateDto updatedTimeSlot) {
        return timeSlotService.updateTimeSlot(timeSlotId, updatedTimeSlot);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getAvailableTimeSlots")
    public List<TimeSlotDto> getAvailableTimeSlots(@RequestParam(defaultValue = "0") int page) {
        return timeSlotService.getAvailableTimeSlots(page, 10);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getAvailableTimeSlotsByUser", key = "#userId")
    public List<TimeSlotDto> getAvailableTimeSlotsByUser(@PathVariable Long userId,
                                                         @RequestParam(defaultValue = "0") int page) {
        return timeSlotService.getAvailableTimeSlotsByUser(userId, page, 10);
    }

    @GetMapping("/speciality/{speciality}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getAvailableTimeSlotsBySpeciality", key = "#speciality")
    public List<TimeSlotDto> getAvailableTimeSlotsBySpeciality(@PathVariable Speciality speciality,
                                                               @RequestParam(defaultValue = "0") int page) {
        return timeSlotService.getAvailableTimeSlotsBySpeciality(speciality, page, 10);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAllTimeSlotsByUser(@PathVariable Long userId) {
        timeSlotService.deleteAllTimeSlotsByUser(userId);
        return Responses.DELETED_ALL_USERS_TIMESLOTS.formatted(userId);
    }

}