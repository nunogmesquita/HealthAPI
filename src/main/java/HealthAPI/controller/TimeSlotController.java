package HealthAPI.controller;

import HealthAPI.dto.timeSlot.TimeSlotDto;
import HealthAPI.dto.timeSlot.TimeSlotUpdateDto;
import HealthAPI.dto.timeSlot.WeeklyTimeSlotDto;
import HealthAPI.messages.Responses;
import HealthAPI.model.Speciality;
import HealthAPI.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @PostMapping("")
    public ResponseEntity<String> generateWeeklyTimeSlots(@RequestBody WeeklyTimeSlotDto weeklyTimeSlotDto) {
        timeSlotService.generateWeeklyTimeSlots(weeklyTimeSlotDto);
        return ResponseEntity.ok(Responses.TIMESLOTS_CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeSlot(@PathVariable Long timeSlotId) {
        timeSlotService.deleteTimeSlot(timeSlotId);
        return ResponseEntity.ok(Responses.DELETED_TIMESLOT.formatted(timeSlotId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TimeSlotDto> updateTimeSlot(@PathVariable Long timeSlotId,
                                                      @RequestBody TimeSlotUpdateDto updatedTimeSlot) {
        TimeSlotDto timeSlot = timeSlotService.updateTimeSlot(timeSlotId, updatedTimeSlot);
        return ResponseEntity.ok(timeSlot);
    }

    @GetMapping("/available")
    public ResponseEntity<List<TimeSlotDto>> getAvailableTimeSlots(@RequestParam(defaultValue = "0") int page) {
        List<TimeSlotDto> timeSlotDtos = timeSlotService.getAvailableTimeSlots(page, 10);
        return ResponseEntity.ok(timeSlotDtos);
    }

    @GetMapping("/available/user/{userId}")
    public ResponseEntity<List<TimeSlotDto>> getAvailableTimeSlotsByUser(@PathVariable Long userId,
                                                                         @RequestParam(defaultValue = "0") int page) {
        List<TimeSlotDto> timeSlotDtos = timeSlotService.getAvailableTimeSlotsByUser(userId, page, 10);
        return ResponseEntity.ok(timeSlotDtos);
    }

    @GetMapping("/available/speciality/{speciality}")
    public ResponseEntity<List<TimeSlotDto>> getAvailableTimeSlotsBySpeciality(@PathVariable Speciality speciality,
                                                                               @RequestParam(defaultValue = "0") int page) {
        List<TimeSlotDto> timeSlotDtos = timeSlotService.getAvailableTimeSlotsBySpeciality(speciality, page, 10);
        return ResponseEntity.ok(timeSlotDtos);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteAllTimeSlotsByUser(@PathVariable Long userId) {
        timeSlotService.deleteAllTimeSlotsByUser(userId);
        return ResponseEntity.ok(Responses.DELETED_ALL_USERS_TIMESLOTS.formatted(userId));
    }

}