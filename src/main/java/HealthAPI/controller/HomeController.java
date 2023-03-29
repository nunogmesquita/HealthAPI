package HealthAPI.controller;

import HealthAPI.dto.user.ProfessionalDto;
import HealthAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor

public class HomeController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<byte[]> getHomepage() throws IOException {
        InputStream gifStream = getClass().getResourceAsStream("/giphy.gif");
        byte[] gifBytes = IOUtils.toByteArray(gifStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_GIF);
        return new ResponseEntity<>(gifBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/services")
    public ResponseEntity<List<String>> getServices() {
        List<String> services = userService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/professionals")
    public ResponseEntity<List<ProfessionalDto>> getProfessionals() {
        List<ProfessionalDto> professionals = userService.getAllProfessionals();
        return ResponseEntity.ok(professionals);
    }

}