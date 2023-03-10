package com.mingati.kikunditestrepo.dto;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;


import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class UserDto {
    @NotEmpty(message = "first name must not be empty")
    private String firstName;
    @NotEmpty(message = "phone must not be empty")
    private String phone;
    @NotEmpty(message = "email must not be empty")
    private String email;

    @NotEmpty(message = "password must not be empty")
    private String password;

    public UserDto hashPassword() {
        this.password = DigestUtils.sha256Hex(password);
        return this;
    }

}
