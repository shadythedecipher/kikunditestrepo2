package com.mingati.kikunditestrepo.listener;

import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.events.EmailEvent;;
import com.mingati.kikunditestrepo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.sendgrid.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.io.IOException;
import java.util.UUID;
@Slf4j
@Component
public class EmailListener implements ApplicationListener<EmailEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(EmailEvent event) {

        //Create the Verification Token for the User with Link
        UserDto user = event.getDto();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationTokenForUser(token,user);
        //Send Mail to user
        String url =
                event.getToken()
                        + "/verifyRegistration?token="
                        + token;

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);
    }

}
