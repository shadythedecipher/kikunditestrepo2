package com.mingati.kikunditestrepo.base;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(toBuilder = true)
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userTable")
@Entity
public class UserBo extends BaseBo {

    private final static long  serialVersionUID = 23435345634342L;

    @Column(name ="first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;
    @Column(name ="phone")
    private String phone;
    @Column
    private String email;
    @Column
    private String password;


}