package HealthAPI.controller;

import HealthAPI.dto.TimeSlot.TimeSlotDto;
import HealthAPI.dto.TimeSlot.TimeSlotUpdateDto;
import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto;
import HealthAPI.dto.User.UserDto;
import HealthAPI.messages.Responses;
import HealthAPI.model.Speciality;
import HealthAPI.service.TimeSlotService;
import HealthAPI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;
    private final UserService userService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService, UserService userService) {
        this.timeSlotService = timeSlotService;
        this.userService = userService;
    }

    @PostMapping("/create/weekly")
    public ResponseEntity<String> generateWeeklyTimeSlots(@RequestBody WeeklyTimeSlotDto weeklyTimeSlotDto) {
        timeSlotService.generateWeeklyTimeSlots(weeklyTimeSlotDto);
        return new ResponseEntity<>(Responses.TIMESLOTS_CREATED, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> generateTimeSlot(@RequestBody WeeklyTimeSlotDto weeklyTimeSlotDto) {
        timeSlotService.generateWeeklyTimeSlots(weeklyTimeSlotDto);
        return new ResponseEntity<>(Responses.TIMESLOTS_CREATED, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<TimeSlotDto>> getAvailableTimeSlots(@RequestParam(defaultValue = "0") int page) {
        List<TimeSlotDto> timeSlotDtos = timeSlotService.getAvailableTimeSlots(page, page + 10);
        return new ResponseEntity<>(timeSlotDtos, HttpStatus.OK);
    }

    @GetMapping("/available/{userId}")
    public ResponseEntity<List<TimeSlotDto>> getAvailableTimeSlotsByUser(@PathVariable Long userId,
                                                         @RequestParam(defaultValue = "0") int page) {
        List<TimeSlotDto> timeSlotDtos = timeSlotService.getAvailableTimeSlotsByUser(userId, page, page + 10);
        return new ResponseEntity<>(timeSlotDtos, HttpStatus.OK);
    }

    @GetMapping("/available/{speciality}")
    public ResponseEntity<List<TimeSlotDto>> getAvailableTimeSlotsBySpeciality(@PathVariable Speciality speciality,
                                                               @RequestParam(defaultValue = "0") int page) {
        List<TimeSlotDto> timeSlotDtos = timeSlotService.getAvailableTimeSlotsBySpeciality(speciality, page, page + 10);
        return new ResponseEntity<>(timeSlotDtos, HttpStatus.OK);
    }

    @DeleteMapping("/byUser/{userId}")
    public ResponseEntity<String> deleteAllTimeSlotsByUser(@PathVariable Long userId) {
        timeSlotService.deleteAllTimeSlotsByUser(userId);
        return new ResponseEntity<>(Responses.DELETED_ALL_USERS_TIMESLOTS.formatted(userId), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeSlot(@PathVariable Long id) {
        timeSlotService.deleteTimeSlot(id);
        return new ResponseEntity<>(Responses.DELETED_TIMESLOT.formatted(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlotDto> updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlotUpdateDto updatedTimeSlot) {
        TimeSlotDto timeSlot = timeSlotService.updateTimeSlot(id, updatedTimeSlot);
        return new ResponseEntity<>(timeSlot, HttpStatus.OK);
    }

}