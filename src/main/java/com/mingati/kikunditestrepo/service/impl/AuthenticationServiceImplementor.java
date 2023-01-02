package com.mingati.kikunditestrepo.service.impl;
import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.configuration.MyDatabaseUserDetailsService;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
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
    private JWTUtility jwtUtility;
    @Autowired
    MyDatabaseUserDetailsService userService;



//    @Value("${token.expiry.threshold:2}")
//    private Integer expiryThreshold;

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
//        return Optional.of(loginRequest)
//                .map(d -> d.withPassword(DigestUtils.sha256Hex(d.getPassword())))
//                .flatMap(e -> userRepository.findByEmailAndPassword(e.getEmail(), e.getPassword()))
//                .map(user -> ApiToken.builder().userId(user.getId()).expiryDate(LocalDateTime.now().plusHours(9))
//                        .token(UUID.randomUUID().toString()).build())
//                .map(apiTokenRepository::save)
//                .map(g -> LoginResponse.builder().token(g.getToken()).build())
//                .orElseThrow(( ) -> new UserLoginException("user login credentials invalid"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
//                            DigestUtils.sha256Hex()

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

        return   LoginResponse.builder().token(savedToken.getToken()).build();

    }

    @Override
    public void requestPasswordRequest(String email) throws KikundiEntityNotFound {
        UserBo userBo = userRepository.findByEmail(email)
                .orElseThrow(() -> new KikundiEntityNotFound("email not registered to any customer"));
        //Todo  create another thread to send reset password email

    }

}
