package com.mingati.kikunditestrepo.security;

import com.mingati.kikunditestrepo.base.BaseBo;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = " api_token")
public class ApiToken extends BaseBo {
    @Column
    private String token;
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
    @Column(name = "user_id")
    private Long userId;
}
