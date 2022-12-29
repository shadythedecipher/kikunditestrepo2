package com.mingati.kikunditestrepo.listener;

import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.events.OTPEvent;;
import com.mingati.kikunditestrepo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j
@Component
public class OTPListener implements ApplicationListener<OTPEvent> {


    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(OTPEvent event) {

        //Create the Verification Token for the User with Link
        UserDto user = event.getDto();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token,user);
        //Send Mail to user
        String url =
                event.getOtp()
                        + "/verifyRegistration?token="
                        + token;

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);

    }
}
