package com.mingati.kikunditestrepo.base;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBoRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_gen")
    @SequenceGenerator(name = "user_role_gen", sequenceName = "user_role_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String role;
    @OneToOne(mappedBy = "user")
    private UserBo userBo;

}
