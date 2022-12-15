package com.mingati.kikunditestrepo.controller;

import com.mingati.kikunditestrepo.response.ApiResponse;
import com.mingati.kikunditestrepo.security.LoginRequest;
import com.mingati.kikunditestrepo.security.LoginResponse;
import com.mingati.kikunditestrepo.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping(value = "login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return Optional.of(loginRequest)
                .map(authenticationService::login).get();
    }
    @GetMapping(value = "/reset-password-request/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> requestPasswordRequest(@PathVariable("email") String email) {
        return Optional.of(email)
                .map(e -> {
                    authenticationService.requestPasswordRequest(e);
                    return "Email to reset password has been sent";
                })
                .map(resp -> ApiResponse.<String>builder()
                        .hasError(false).errors(List.of())
                        .successMessage(resp)
                        .build()).get();
    }

}
