package com.mingati.kikunditestrepo.service;


import com.mingati.kikunditestrepo.exception.KikundiEntityNotFound;
import com.mingati.kikunditestrepo.security.LoginRequest;
import com.mingati.kikunditestrepo.security.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    LoginResponse login(LoginRequest loginRequest) throws Exception;

    void requestPasswordRequest(String email) throws KikundiEntityNotFound;
}
