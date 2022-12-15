package com.mingati.kikunditestrepo.security;

import lombok.*;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiTokenDto {
    private String token;
    private Long userId;
}
