package com.mingati.kikunditestrepo.repository;

import com.mingati.kikunditestrepo.base.VerificationOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationOtpRepository extends JpaRepository<VerificationOtp, Long> {
    VerificationOtp findByOtp(String otp);
}
