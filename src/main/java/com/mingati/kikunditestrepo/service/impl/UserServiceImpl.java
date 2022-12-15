package com.mingati.kikunditestrepo.service.impl;


import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.exception.CreationValidationException;
import com.mingati.kikunditestrepo.otp.sendSMS;
import com.mingati.kikunditestrepo.repository.UserRepository;
import com.mingati.kikunditestrepo.service.UserService;
import com.mingati.kikunditestrepo.validator.user.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private List<UserValidator> UserValidatorList;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public sendSMS sendMS;

    @Override
    public UserDto createCustomer(UserDto userDto) {

        String validationMessage = UserValidatorList.parallelStream()
                        .map(e -> e.validate(userDto))
                        .filter(StringUtils::isNotEmpty)
                        .findFirst()
                        .orElseGet(() -> StringUtils.EMPTY);
        if (StringUtils.isNotEmpty(validationMessage)) throw new CreationValidationException(validationMessage);
        String res = sendMS.sendSms();
        System.out.println("this is a response" + res);
        return Optional.of(userDto).map(UserDto::hashPassword)
                .map(customer -> modelMapper.map(customer, UserBo.class))
                .map(userRepository::save)
                .map(customerBo -> modelMapper.map(customerBo, UserDto.class))
                .get();

    }
}
