package com.mingati.kikunditestrepo.repository;


import com.mingati.kikunditestrepo.security.ApiToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ApiTokenRepository extends JpaRepository<ApiToken,Long> {
    Optional<ApiToken> findByToken(String token);
}
