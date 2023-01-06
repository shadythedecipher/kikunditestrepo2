package com.mingati.kikunditestrepo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class OTPDto {
    @NotEmpty(message = "OTP must not be empty")
    private String otp;
    @NotEmpty(message = "email must not be empty")
    private String email;

}
