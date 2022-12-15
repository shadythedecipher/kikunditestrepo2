package com.mingati.kikunditestrepo.security;

import lombok.*;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
