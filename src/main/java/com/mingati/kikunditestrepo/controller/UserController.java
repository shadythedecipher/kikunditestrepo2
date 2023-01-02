package com.mingati.kikunditestrepo.controller;

import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.dto.OTPDto;
import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.events.EmailEvent;
import com.mingati.kikunditestrepo.events.OtpEvent;
import com.mingati.kikunditestrepo.response.ApiResponse;
import com.mingati.kikunditestrepo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class UserController {
    int randomNumber;
    public final UserService service;
    @Autowired
    ApplicationEventPublisher publisherEvent;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/create")
    public ApiResponse<UserDto> createCustomer(@Valid @RequestBody UserDto userdto, final HttpServletRequest request) {

        UserDto resp= service.createCustomer(userdto);
        if(resp.getEmail()==null){
            return ApiResponse.<UserDto>builder()
                        .responseObject(null)
                        .hasError(true)
                        .successMessage("Failed to create kikundi user")
                        .build();

        }else {

            publisherEvent.publishEvent(new EmailEvent(resp, applicationUrl(request)));
            publisherEvent.publishEvent(new OtpEvent(resp, applicationUrl(request)));
            return ApiResponse.<UserDto>builder()
                        .responseObject(null)
                        .hasError(false)
                        .successMessage("created successfully")
                        .build();
        }
    }

    @GetMapping("/verifyRegistration")
    public ApiResponse<String> verifyRegistration(@RequestParam("token") String token) {
        String result = service.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")) {
            return ApiResponse.<String>builder().responseObject(null).hasError(false).successMessage("User verified successfully").build();
        }
        return ApiResponse.<String>builder().responseObject(null).hasError(true).successMessage("Expired token failed to verify a user").build();
    }
    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/verifyOTP")
    public ApiResponse<String> verifyOTP(@Valid @RequestBody OTPDto dto) {

        Optional<UserBo> userToBeVerified= service.getUser(dto.getUsername());
        if (userToBeVerified==null){

             return ApiResponse.<String>builder()
                    .responseObject(null)
                    .hasError(true)
                    .successMessage("No user for the given OTPFailed to verify OTP Try Again")
                    .build();
        }
        else {
            UserBo foundUser = userToBeVerified.get();
            String result = service.validateVerificationOTP(foundUser.getId());
            if(result.equalsIgnoreCase("valid")) {
                return ApiResponse.<String>builder().responseObject(null).hasError(false).successMessage("Phone number verified successfully").build();
            }
            return ApiResponse.<String>builder().responseObject(null).hasError(true).successMessage("Expired OTP failed to verify OTP").build();

        }

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/resendVerifyOTP")
    public ApiResponse<String> resendOTP() {

        return ApiResponse.<String>builder()
                .successMessage("ss")
                .errors(null)
                .responseObject("Working on Resend")
                .build();

    }
    @GetMapping(value = "/greetings")
    public ResponseEntity<String> greetings(){

        return ResponseEntity.ok().body("hi there welcome to kikundi api version 2");
    }
    @GetMapping(value = "/greeting")
    public ResponseEntity<String> greeting(){

        return ResponseEntity.ok().body("hi there welcome to kikundi api version 2");
    }


}
