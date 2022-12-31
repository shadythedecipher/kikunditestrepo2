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

        // Recipient's email ID needs to be mentioned.
        String to = user.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "kikundidev@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}
