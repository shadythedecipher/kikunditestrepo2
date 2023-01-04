package com.mingati.kikunditestrepo.service.impl;
import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.configuration.MyDatabaseUserDetailsService;
import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.events.EmailEvent;
import com.mingati.kikunditestrepo.exception.KikundiEntityNotFound;
import com.mingati.kikunditestrepo.exception.UserLoginException;
import com.mingati.kikunditestrepo.repository.ApiTokenRepository;
import com.mingati.kikunditestrepo.repository.UserRepository;
import com.mingati.kikunditestrepo.security.ApiToken;
import com.mingati.kikunditestrepo.security.JWTUtility;
import com.mingati.kikunditestrepo.security.LoginRequest;
import com.mingati.kikunditestrepo.security.LoginResponse;
import com.mingati.kikunditestrepo.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthenticationServiceImplementor implements AuthenticationService {


    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ApiTokenRepository apiTokenRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    ApplicationEventPublisher publisher;
    @Autowired
    ModelMapper mapper;
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    MyDatabaseUserDetailsService userService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()

                    )
            );
        } catch (BadCredentialsException e) {
            throw new UserLoginException("user login credentials invalid");
        }
        final UserDetails userDetails
                = userService.loadUserByUsername(loginRequest.getEmail());
        UserBo bo= userRepository.findByEmail(loginRequest.getEmail()).get();

        final String token =
                jwtUtility.generateToken(userDetails);
       ApiToken tokenWIthUserId= ApiToken.builder().userId(bo.getId()).expiryDate(LocalDateTime.now().plusHours(9))
                        .token(token).build();
       ApiToken savedToken= apiTokenRepository.save(tokenWIthUserId);

        return   LoginResponse.builder().token(savedToken.getToken()).firstName(bo.getFirstName()).build();

    }

    @Override
    public void requestPasswordRequest(String email) throws KikundiEntityNotFound {
        UserBo userBo = userRepository.findByEmail(email)
                .orElseThrow(() -> new KikundiEntityNotFound("email not registered to any customer"));
      UserDto dto= mapper.map(userBo,UserDto.class);
        publisher.publishEvent(new EmailEvent(dto, "2562"));
    }
}
