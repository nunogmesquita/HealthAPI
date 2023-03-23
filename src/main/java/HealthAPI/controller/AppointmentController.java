package HealthAPI.controller;

import HealthAPI.dto.AppointmentResponse;
import HealthAPI.dto.BaseResponse;
import HealthAPI.model.Appointment;
import HealthAPI.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/appointment")
public class AppointmentController extends ApiCrudController {

    @Autowired
    private AppointmentService appointmentService;


    @Override
    public BaseResponse<Appointment> getById(Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment != null) {
            return new BaseResponse<>(appointment, "FETCHED");
        } else {
            return new BaseResponse<>(null, "NOT FOUND");
        }
    }


    @Override
    public BaseResponse<Appointment> deleteById(Long id) {
        Appointment appointment = appointmentService.deleteById(id);
        if (appointment != null) {
            return new BaseResponse<>(appointment, "DELETED");
        } else {
            return new BaseResponse<>(null, "NOT FOUND");
        }
    }

    @Override
    public BaseResponse<Appointment> restoreById(Long id) {
        ResponseEntity<?> responseEntity = appointmentService.restoreById(id);
        if (responseEntity != null) {
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return new BaseResponse<>((Appointment) responseEntity.getBody(), "RESTORED");
            } else if (responseEntity.getStatusCode().is4xxClientError()) {
                return new BaseResponse<>(null, Objects.requireNonNull(responseEntity.getBody()).toString());
            }
        }
        return new BaseResponse<>(null, "NOT FOUND");
    }


    @GetMapping("/getAllByClientId")
    public AppointmentResponse getAllAppointmentByClientId(@RequestParam Long clientId) {
        List<Appointment> appointmentList = appointmentService.findAllByClientId(clientId);

        if (appointmentList == null || appointmentList.size() == 0) {
            return new AppointmentResponse(null, "NOT AVAILABLE");
        } else {
            return new AppointmentResponse(appointmentList, "FETCHED");
        }
    }

    @GetMapping("/getAllByHcpId")
    public AppointmentResponse getAllAppointmentByHcpId(@RequestParam Long hcpId) {
        List<Appointment> appointmentList = appointmentService.findAllByHcpId(hcpId);
        if (appointmentList == null || appointmentList.size() == 0) {
            return new AppointmentResponse(null, "NOT AVAILABLE");
        } else {
            return new AppointmentResponse(appointmentList, "FETCHED");
        }
    }
}
