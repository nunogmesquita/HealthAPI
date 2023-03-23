package HealthAPI.controller;

import HealthAPI.dto.BaseResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


abstract class ApiCrudController {

    @GetMapping("/getById")
    public abstract BaseResponse<?> getById(@RequestParam Long id);

    @DeleteMapping("/deleteById")
    public abstract BaseResponse<?> deleteById(@RequestParam Long id);

    @PutMapping("/restoreById")
    public abstract BaseResponse<?> restoreById(@RequestParam Long id);
}
