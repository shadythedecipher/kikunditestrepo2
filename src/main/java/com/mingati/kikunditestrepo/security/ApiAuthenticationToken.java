//package com.mingati.kikunditestrepo.security;
//
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class ApiAuthenticationToken extends AbstractAuthenticationToken {
//
//    @Getter
//    private ApiTokenDto tokenDto;
//    @Getter
//    private String token;
//
//    public ApiAuthenticationToken(final String token) {
//        super(null);
//        this.token = token;
//    }
//
//
//    public ApiAuthenticationToken(final ApiTokenDto tokenDto, Collection<GrantedAuthority> grantedAuthorities) {
//        super(grantedAuthorities);
//        this.tokenDto = tokenDto;
//        setAuthenticated(true);
//    }
//
//    @Override
//    public ApiTokenDto getCredentials() {
//        return tokenDto;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return tokenDto;
//    }
//}
