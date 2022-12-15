package com.mingati.kikunditestrepo.repository;

import com.mingati.kikunditestrepo.base.UserBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserBo,Long> {
    Optional<UserBo> findByEmail(String email);

    Optional<UserBo> findByEmailAndPassword(String username, String password);

}
