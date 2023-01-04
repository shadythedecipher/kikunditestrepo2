package com.mingati.kikunditestrepo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationOtp {

    //Expiration time 10 minutes
    private static  final int EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_otp_gen")
    @SequenceGenerator(name = "verification_otp_gen", sequenceName = "verification_otp_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    //token/otp
    private String otp;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private UserBo user;
    @Column(nullable = false)
    String userName;




    public VerificationOtp(UserBo user, String otp) {
        super();
        this.otp = otp;
        this.user = user;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public VerificationOtp(String otp) {
        super();
        this.otp = otp;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public VerificationOtp(UserBo userbo, String otp, String email) {
        super();
        this.otp = otp;
        this.user = userbo;
        this.userName=email;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
