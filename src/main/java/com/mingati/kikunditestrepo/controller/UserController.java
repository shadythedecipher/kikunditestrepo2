package com.mingati.kikunditestrepo.controller;

import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.response.ApiResponse;
import com.mingati.kikunditestrepo.service.UserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService service;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/create")
    public ApiResponse<UserDto> createCustomer(@Valid @RequestBody UserDto userdto) {
//        Twilio.init(System.getenv("AC1d0018c82893b0289bb12c7d3f0dbcab"), System.getenv("Redacted"));
//
//        Message.creator(new PhoneNumber("+255714867576"),
//                new PhoneNumber("MGfa76020f63c18e08d8a70cd8fb4a4a2d"), "Hello from Twilio 📞").create();
        return Optional.of(userdto)
                .map(service::createCustomer)
                .map(resp -> ApiResponse.<UserDto>builder()
                        .responseObject(resp)
                        .hasError(false)
                        .successMessage("created successfully with id %d ")
                        .build()).get();


    }
    @GetMapping(value = "/greetings")
    public ResponseEntity<String> greetings(){

        return ResponseEntity.ok().body("hi there welcome to kikundi api version 2");
    }

}
