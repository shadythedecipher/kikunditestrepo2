package com.mingati.kikunditestrepo.service.impl;


import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.base.VerificationToken;
import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.exception.CreationValidationException;
import com.mingati.kikunditestrepo.repository.ApiTokenRepository;
import com.mingati.kikunditestrepo.repository.UserRepository;
import com.mingati.kikunditestrepo.repository.VerificationTokenRepository;
import com.mingati.kikunditestrepo.service.UserService;
import com.mingati.kikunditestrepo.validator.user.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private List<UserValidator> UserValidatorList;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createCustomer(UserDto userDto) {

        String validationMessage = UserValidatorList.parallelStream()
                        .map(e -> e.validate(userDto))
                        .filter(StringUtils::isNotEmpty)
                        .findFirst()
                        .orElseGet(() -> StringUtils.EMPTY);
        if (StringUtils.isNotEmpty(validationMessage)) throw new CreationValidationException(validationMessage);
        return Optional.of(userDto).map(UserDto::hashPassword)
                .map(customer -> modelMapper.map(customer, UserBo.class))
                .map(userRepository::save)
                .map(customerBo -> modelMapper.map(customerBo, UserDto.class))
                .get();

    }

    @Override
    public void saveVerificationTokenForUser(String token, UserDto user) {
        UserBo userbo=modelMapper.map(user, UserBo.class);

        VerificationToken verificationToken
                = new VerificationToken(userbo, token);

        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "invalid";
        }

        UserBo user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
}
