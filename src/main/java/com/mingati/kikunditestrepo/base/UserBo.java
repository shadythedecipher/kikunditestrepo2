package com.mingati.kikunditestrepo.base;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
@Entity
public class UserBo {

    private final static long  serialVersionUID = 23435345634342L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_table_gen")
    @SequenceGenerator(name = "user_table_gen", sequenceName = "user_table_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name ="first_name")
    private String firstName;
//    @Column(name ="last_name")
//    private String lastName;
    @Column(name ="phone")
    private String phone;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    boolean enabled;
    @Column(name = "date_created")
    protected LocalDateTime dateCreated= LocalDateTime.now();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserBoRole user;

}