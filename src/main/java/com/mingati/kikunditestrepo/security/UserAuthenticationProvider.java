//package com.mingati.kikunditestrepo.security;
//
//import com.mingati.kikunditestrepo.repository.ApiTokenRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.Optional;
//
//
//public class UserAuthenticationProvider implements AuthenticationProvider {
//    @Autowired
//    private ApiTokenRepository apiTokenRepository;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        ApiAuthenticationToken apiAuthenticationToken = (ApiAuthenticationToken) authentication;
//
//        ApiToken apiToken = apiTokenRepository.findByToken(apiAuthenticationToken.getToken())
//                .orElseThrow(() -> new BadCredentialsException("Token not found "));
//        return Optional.of(apiToken)
//                .filter(e -> e.getExpiryDate().isAfter(LocalDateTime.now()))
//                .map(f -> new ApiAuthenticationToken(ApiTokenDto.builder()
//                        .token(apiToken.getToken() )
//                        .userId(apiToken.getUserId()).build(), Collections.emptyList()))
//                .orElseThrow(() -> new BadCredentialsException("Token is expired"));
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(ApiAuthenticationToken.class);
//    }
//}
