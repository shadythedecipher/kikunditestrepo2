package com.mingati.kikunditestrepo.controller;

import com.mingati.kikunditestrepo.response.ApiResponse;
import com.mingati.kikunditestrepo.security.LoginRequest;
import com.mingati.kikunditestrepo.security.LoginResponse;
import com.mingati.kikunditestrepo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping(value = "api/user/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        if (loginRequest == null) throw new NullPointerException();
        LoginResponse login = authenticationService.login(loginRequest);
        if (login == null) throw new java.util.NoSuchElementException("No value present");
        return ResponseEntity.ok().body(ApiResponse.builder()
                        .successMessage("User login successful")
                        .responseObject(login)
                .errors(null).build());
    }
    @GetMapping(value = "api/user/reset-password-request/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> requestPasswordRequest(@PathVariable("email") String email) {
        return Optional.of(email)
                .map(e -> {
                    authenticationService.requestPasswordRequest(e);
                    return ResponseEntity.ok().body( ApiResponse.builder().hasError(false).responseObject(null).errors(null).successMessage("Email to reset password has been sent").build() );
                })
                .map(resp -> ApiResponse.<String>builder()
                        .hasError(false).errors(List.of())
                        .successMessage(String.valueOf(resp.getBody()))
                        .build()).get();
    }

}
