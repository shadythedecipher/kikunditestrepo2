package com.mingati.kikunditestrepo.repository;

import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.base.VerificationOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationOtpRepository extends JpaRepository<VerificationOtp, Long> {

    void deleteByUserName(String email);

    List<VerificationOtp> findByUserName(String userId);
//    Optional<VerificationOtp> findByUserName(String userId);
}
