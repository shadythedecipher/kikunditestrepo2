package com.mingati.kikunditestrepo.service;

import com.mingati.kikunditestrepo.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * creates user and validates submission information
     * @param userDto
     * @return
     */
    UserDto createCustomer(UserDto userDto);

    void saveVerificationTokenForUser(String token, UserDto user);

    String validateVerificationToken(String token);

    void saveVerificationOtpForUser(String otp, UserDto user);
}
