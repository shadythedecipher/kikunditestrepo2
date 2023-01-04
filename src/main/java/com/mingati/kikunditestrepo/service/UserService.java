package com.mingati.kikunditestrepo.service;

import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.dto.UserDto;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

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
    String validateVerificationOTP(String userId, @NotEmpty String otp);

    void saveVerificationOtpForUser(String otp, UserDto user);

    Optional<UserBo> getUser(String username);

    void saveResendVerificationOtpForxUser(String otp, UserDto savingDTOT);
}
