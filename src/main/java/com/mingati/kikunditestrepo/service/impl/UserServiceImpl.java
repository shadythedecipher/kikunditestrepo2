package com.mingati.kikunditestrepo.service.impl;


import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.base.UserBoRole;
import com.mingati.kikunditestrepo.base.VerificationOtp;
import com.mingati.kikunditestrepo.base.VerificationToken;
import com.mingati.kikunditestrepo.dto.UserDto;
import com.mingati.kikunditestrepo.exception.CreationValidationException;
import com.mingati.kikunditestrepo.repository.UserRepository;
import com.mingati.kikunditestrepo.repository.UserRoleRepository;
import com.mingati.kikunditestrepo.repository.VerificationOtpRepository;
import com.mingati.kikunditestrepo.repository.VerificationTokenRepository;
import com.mingati.kikunditestrepo.service.UserService;
import com.mingati.kikunditestrepo.validator.user.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
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
    VerificationOtpRepository verificationOtpRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
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
        UserBo userbo=userRepository.findByEmail(user.getEmail()).get();

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
      UserBoRole role= new UserBoRole();
      role.setRole("User");
     UserBoRole savedRole= userRoleRepository.save(role);
        System.out.println("%$55$^%4%$4$5"+savedRole.getId());


//        System.out.println("$$$$$$$$$$$$"+ user.toString());
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        user.setUser(savedRole);
        user.setEnabled(true);
       UserBo saved= userRepository.save(user);
        System.out.println("BBBBBBBBBBBBBB$%%$#$##$#$#$4$$$$$$$$$$$$"+ saved);
        return "valid";
    }
    public String validateVerificationOTP(String userId, @NotEmpty String otp) {
        VerificationOtp verificationOtp
                = verificationOtpRepository.findByUserName( userId).get(0);
        System.out.println("your otp is from the database" +verificationOtp.getOtp());
        if (verificationOtp.getOtp()==null){
            return "invalid";
        }
        else{

                    if(verificationOtp.getOtp().equals(otp)){
//                        UserBo user = verifiedOTP.getUser();
//                        UserBoRole role= new UserBoRole();
//                        role.setRole("User");
////                        UserBoRole savedRole= userRoleRepository.save(role);
//                        System.out.println("%$55$^%4%$4$5"+savedRole.getId());


//        System.out.println("$$$$$$$$$$$$"+ user.toString());
                        Calendar cal = Calendar.getInstance();

                        if ((verificationOtp.getExpirationTime().getTime()
                                - cal.getTime().getTime()) <= 0) {
                            verificationOtpRepository.delete(verificationOtp);
                            return "expired";
                        }
//                        user.setUser(savedRole);
//                        user.setEnabled(true);
//                        UserBo saved= userRepository.save(user);
//                        System.out.println("BBBBBBBBBBBBBB$%%$#$##$#$#$4$$$$$$$$$$$$"+ saved);
                        return "valid";
                    }else{

                        return "invalid";
                    }

        }

    }

    @Override
    public void saveVerificationOtpForUser(String otp, UserDto user) {
        UserBo userbo=userRepository.findByEmail(user.getEmail()).get();
        VerificationOtp otp1= new VerificationOtp(userbo,otp,userbo.getEmail());
        verificationOtpRepository.save(otp1);

    }

    @Override
    public Optional<UserBo> getUser(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public void saveResendVerificationOtpForxUser(String otp, UserDto savingDTOT) {
        UserBo userbo=userRepository.findByEmail(savingDTOT.getEmail()).get();
        List<VerificationOtp> exist = verificationOtpRepository.findByUserName(userbo.getEmail());
        if(exist!=null){
            verificationOtpRepository.deleteInBatch(exist);
            System.out.println("deleteeeedddddddddddddddddddddddddddddddddddd  in the repo");
            System.out.println("saving new user in the ellse statement otp");
            VerificationOtp otp1= new VerificationOtp(userbo,otp,userbo.getEmail());
            verificationOtpRepository.save(otp1);
        }else {
            VerificationOtp otp1= new VerificationOtp(userbo,otp,userbo.getEmail());
            verificationOtpRepository.save(otp1);
        }

    }
}
