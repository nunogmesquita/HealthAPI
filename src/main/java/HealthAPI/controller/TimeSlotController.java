package HealthAPI.controller;

import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto;
import HealthAPI.dto.User.UserDto;
import HealthAPI.messages.Responses;
import HealthAPI.model.Speciality;
import HealthAPI.model.TimeSlot;
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

    @PostMapping("/create")
    public ResponseEntity<String> generateWeeklyTimeSlots(@NonNull HttpServletRequest request,
                                                          @RequestBody WeeklyTimeSlotDto weeklyTimeSlotDto) {
        String jwt = request.getHeader("Authorization").substring(7);
        UserDto userDto = userService.getUserByToken(jwt);
        timeSlotService.generateWeeklyTimeSlots(weeklyTimeSlotDto, userDto);
        return new ResponseEntity<>(Responses.TIMESLOTS_CREATED, HttpStatus.OK);
    }

    @GetMapping("/available")
    public List<TimeSlot> getAvailableTimeSlots(@RequestParam(defaultValue = "0") int page) {
        return timeSlotService.getAvailableTimeSlots(page, page + 10);
    }

    @GetMapping("/available/{userId}")
    public List<TimeSlot> getAvailableTimeSlotsByUser(@PathVariable Long userId,
                                                      @RequestParam(defaultValue = "0") int page) {
        return timeSlotService.getAvailableTimeSlotsByUser(userId, page, page + 10);
    }

    @GetMapping("/available/speciality/{speciality}")
    public List<TimeSlot> getAvailableTimeSlotsBySpeciality(@PathVariable Speciality speciality,
                                                            @RequestParam(defaultValue = "0") int page) {
        return timeSlotService.getAvailableTimeSlotsBySpeciality(speciality, page, page + 10);
    }

    @DeleteMapping("/byUser/{userId}")
    public void deleteAllTimeSlotsByUser(@PathVariable Long userId) {
        timeSlotService.deleteAllTimeSlotsByUser(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeSlot(@PathVariable Long id) {
        timeSlotService.deleteTimeSlot(id);
    }

    @PutMapping("/{id}")
    public void updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlot updatedTimeSlot) {
        timeSlotService.updateTimeSlot(id, updatedTimeSlot);
    }

}