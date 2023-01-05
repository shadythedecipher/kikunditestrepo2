package com.mingati.kikunditestrepo.listener;

import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.events.OtpEvent;
import com.mingati.kikunditestrepo.service.UserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;
@Slf4j
@Component
public class OtpListener implements ApplicationListener<OtpEvent> {


    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(OtpEvent event) {
        //Create the Verification Otp for the User with Link
        UserDto user = event.getDto();
        final String ACCOUNT_SID = Configfile.ACCOUNT_SID;
        final String AUTH_TOKEN = Configfile.AUTH_TOKEN;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Twilio.setUsername(ACCOUNT_SID);
        Twilio.setPassword(AUTH_TOKEN);
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        String otp= String.valueOf(randomNumber);
        Message message =


                Message.creator(

                        new com.twilio.type.PhoneNumber(user.getPhone()),
                        new com.twilio.type.PhoneNumber("+12536525117"),
                        "Otp is "+randomNumber)
                .create();
        userService.saveVerificationOtpForUser(otp,user);
        //Send otp to user
        String url =
                event.getOtp()
                        + "/verifyRegistration?token="
                        + otp;

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);
    }
}
