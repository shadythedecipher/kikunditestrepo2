package com.mingati.kikunditestrepo.validator.user;

import com.mingati.kikunditestrepo.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class PasswordValidator implements UserValidator {
    /**
     * At least one upper case English letter, (?=.*?[A-Z])
     * At least one lower case English letter, (?=.*?[a-z])
     * At least one digit, (?=.*?[0-9])
     * At least one special character, (?=.*?[#?!@$%^&*-])
     * Minimum eight in length .{8,} (with the anchors)
     *
     * @param userDto
     * @return
     */

    @Override
    public String validate(UserDto userDto) {
        return Optional.of(userDto)
                .map(UserDto::getPassword)
                .filter(pwd -> pwd.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"))
                .map(f -> StringUtils.EMPTY)
                .orElseGet(() -> "Password Invalid");

    }

}
