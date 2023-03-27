package HealthAPI.controller;

import HealthAPI.dto.Client.ClientCreateDto;
import HealthAPI.dto.Client.ClientDto;
import HealthAPI.messages.Responses;
import HealthAPI.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/myaccount")
    public ResponseEntity<ClientDto> viewMyAccount(@NonNull HttpServletRequest header) {
        String jwt = header.getHeader("Authorization").substring(7);
        ClientDto clientDto = clientService.getClientByToken(jwt);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PatchMapping("/myAccount")
    public ResponseEntity<ClientDto> updateMyAccount(@NonNull HttpServletRequest request, @Valid @RequestBody ClientCreateDto clientCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
            }
        }
        String jwt = request.getHeader("Authorization").substring(7);
        ClientDto client = clientService.getClientByToken(jwt);
        ClientDto updatedClient = clientService.updateClient(client.getId(), clientCreateDto);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(Responses.DELETED_CLIENT.formatted(id), HttpStatus.OK);
    }

}