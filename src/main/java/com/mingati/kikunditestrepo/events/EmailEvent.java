package com.mingati.kikunditestrepo.events;

import com.mingati.kikunditestrepo.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class EmailEvent extends ApplicationEvent {
    private UserDto dto;
    String token;
    public EmailEvent(UserDto dto, String token) {
        super(dto);
        this.dto=dto;
        this.token=token;
    }

}
