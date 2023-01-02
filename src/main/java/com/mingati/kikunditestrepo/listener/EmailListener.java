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
//        Email from = new Email("KikundiTeam@Dev.com");
//        String subject = "Please click the link below";
//        Email to = new Email(user.getEmail());
//        Content content = new Content("text/plain",  " \"Please click the following link to verify your email: \" " +url);
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (IOException ex) {
//            try {
//                throw ex;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }


    }

}
