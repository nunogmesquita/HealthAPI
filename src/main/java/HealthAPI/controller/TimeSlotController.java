package HealthAPI.controller;

import HealthAPI.dto.BaseResponse;
import HealthAPI.dto.TimeSlotBookingRequest;
import HealthAPI.dto.TimeSlotResponse;
import HealthAPI.dto.TimeSlotStatusRequest;
import HealthAPI.model.Appointment;
import HealthAPI.model.TimeSlot;
import HealthAPI.service.TimeSlotService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/slots")
public class TimeSlotController {

  private TimeSlotService timeSlotService;

  public TimeSlotController(TimeSlotService timeSlotService) {
    this.timeSlotService = timeSlotService;
  }

  @PutMapping("/create")
  public BaseResponse<TimeSlot> createNewTimeSlot(@Valid @RequestBody TimeSlot timeSlot) {
    ResponseEntity<?> response = timeSlotService.createTimeSlot(timeSlot);
    if (response.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((TimeSlot) response.getBody(),
              response.getStatusCode().toString());
    } else if (response.getStatusCode().is4xxClientError() && response.getBody() != null) {
      return new BaseResponse<>(null, response.getBody().toString());
    } else {
      return new BaseResponse<>(null, response.getStatusCode().toString());
    }
  }

  @GetMapping("/getAllByHcpId")
  public TimeSlotResponse getAllTimeSlotByHcpId(@Valid @RequestParam Long hcpId) {
    List<TimeSlot> slots = timeSlotService.getAllByHcpId(hcpId);
    if (slots == null || slots.size() == 0) {
      return new TimeSlotResponse(null, "NOT FOUND");
    } else {
      return new TimeSlotResponse(slots, "FOUND");
    }
  }

  @GetMapping("/liveSlotStatus")
  public TimeSlotResponse getTimeSlotLiveStatus(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                        @RequestParam Long hcpId,
                                        @RequestParam Long clientId) {

    TimeSlotStatusRequest timeSlotStatusRequest = new TimeSlotStatusRequest(hcpId, clientId, date);
    return timeSlotService.getLiveTimeSlotStatus(timeSlotStatusRequest);
  }

  @GetMapping("/getAll")
  public TimeSlotResponse getAllTimeSlot() {
    List<TimeSlot> timeSlotList = timeSlotService.getAll();
    return new TimeSlotResponse(timeSlotList, "DONE");
  }

  @PatchMapping("/book")
  public BaseResponse<Appointment> bookTimeSlotByClientId(@Valid @RequestBody TimeSlotBookingRequest timeSlotRequest) {

    ResponseEntity<?> response = timeSlotService.bookTimeSlot(timeSlotRequest);
    if (response.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Appointment) response.getBody(),
          response.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, response.getStatusCode().toString());
    }
  }

  @DeleteMapping("/deleteById")
  public BaseResponse<TimeSlot> deleteTimeSlotById(@RequestParam Long timeSlotId) {

    TimeSlot deletedTimeSlot = timeSlotService.deleteTimeSlotById(timeSlotId);
    if (deletedTimeSlot == null) {
      return new BaseResponse<>(null, "Bad Request");
    } else {
      return new BaseResponse<>(deletedTimeSlot, "OK");
    }
  }
}
