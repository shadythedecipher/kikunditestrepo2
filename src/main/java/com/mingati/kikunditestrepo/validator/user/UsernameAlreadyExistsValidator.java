package com.mingati.kikunditestrepo.validator.user;

import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsernameAlreadyExistsValidator implements UserValidator{

    @Autowired
    private final UserRepository userRepository;

    @Override
    public String validate(UserDto userDto) {
            return Optional.of(userDto)
                    .map(UserDto::getEmail)
                    .flatMap(userRepository::findByEmail)
                    .map(em -> String.format("Customer Exists with the username %s", em.getEmail()))
                    .orElseGet(() -> StringUtils.EMPTY);


    }
}
