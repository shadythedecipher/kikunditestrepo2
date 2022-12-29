package com.mingati.kikunditestrepo.events;

import com.mingati.kikunditestrepo.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class OTPEvent extends ApplicationEvent {
    private UserDto dto;
    String otp;
    public OTPEvent(UserDto dto, String otp) {
        super(dto);
        this.dto=dto;
        this.otp=otp;
    }

}
