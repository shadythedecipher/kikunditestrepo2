package com.mingati.kikunditestrepo.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class ResendOTPEvent extends ApplicationEvent {


    private String  dto;
    public ResendOTPEvent(String dto) {
        super(dto);
        this.dto=dto;
    }
}
