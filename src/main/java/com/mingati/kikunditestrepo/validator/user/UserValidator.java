package com.mingati.kikunditestrepo.validator.user;

import com.mingati.kikunditestrepo.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserValidator {
    String validate(UserDto userDto);
}
