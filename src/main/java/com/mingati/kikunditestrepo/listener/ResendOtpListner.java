package com.mingati.kikunditestrepo.listener;


import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.events.ResendOTPEvent;
import com.mingati.kikunditestrepo.exception.KikundiEntityNotFound;
import com.mingati.kikunditestrepo.exception.TwilioError;
import com.mingati.kikunditestrepo.service.UserService;
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;
@Slf4j
@Component
public class ResendOtpListner implements ApplicationListener<ResendOTPEvent> {
    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(ResendOTPEvent event) {
        String userEmail = event.getDto();
        UserBo userToResendOtp= userService.getUser(userEmail)
                .orElseThrow(() -> new KikundiEntityNotFound("No Otp for such registered customer"));
        final String ACCOUNT_SID = Configfile.ACCOUNT_SID;
        final String AUTH_TOKEN = Configfile.AUTH_TOKEN;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Twilio.setUsername(ACCOUNT_SID);
        Twilio.setPassword(AUTH_TOKEN);
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        String otp= String.valueOf(randomNumber);
        try
        {
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(userToResendOtp.getPhone()),
                            new com.twilio.type.PhoneNumber("+12536525117"),
                            "Otp is "+randomNumber)
                    .create();

        }catch (TwilioException exception){
            throw new TwilioError("On trial restriction");
        }


        UserDto savingDTOT= UserDto.builder()
                .email(userToResendOtp.getEmail())
                .firstName(userToResendOtp.getFirstName())
                .phone(userToResendOtp.getPhone())
                .password(userToResendOtp.getPassword())
                .build();
        userService.saveResendVerificationOtpForxUser(otp,savingDTOT);
        //Send otp to user
        String url =
                event.getDto()
                        + "/verifyRegistration?token="
                        + otp;

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);
    }
}
