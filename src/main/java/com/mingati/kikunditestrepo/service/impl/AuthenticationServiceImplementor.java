package com.mingati.kikunditestrepo.service.impl;
import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.exception.KikundiEntityNotFound;
import com.mingati.kikunditestrepo.exception.UserLoginException;
import com.mingati.kikunditestrepo.repository.ApiTokenRepository;
import com.mingati.kikunditestrepo.repository.UserRepository;
import com.mingati.kikunditestrepo.security.ApiToken;
import com.mingati.kikunditestrepo.security.LoginRequest;
import com.mingati.kikunditestrepo.security.LoginResponse;
import com.mingati.kikunditestrepo.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Value("${token.expiry.threshold:2}")
//    private Integer expiryThreshold;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return Optional.of(loginRequest)
                .map(d -> d.withPassword(DigestUtils.sha256Hex(d.getPassword())))
                .flatMap(e -> userRepository.findByEmailAndPassword(e.getEmail(), e.getPassword()))
                .map(user -> ApiToken.builder().userId(user.getId()).expiryDate(LocalDateTime.now().plusHours(9))
                        .token(UUID.randomUUID().toString()).build())
                .map(apiTokenRepository::save)
                .map(g -> LoginResponse.builder().token(g.getToken()).build())
                .orElseThrow(( ) -> new UserLoginException("user login credentials invalid"));
    }

    @Override
    public void requestPasswordRequest(String email) throws KikundiEntityNotFound {
        UserBo userBo = userRepository.findByEmail(email)
                .orElseThrow(() -> new KikundiEntityNotFound("email not registered to any customer"));
        //Todo  create another thread to send reset password email

    }

}
