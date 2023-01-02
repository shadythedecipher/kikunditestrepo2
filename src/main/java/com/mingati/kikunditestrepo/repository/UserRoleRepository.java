package com.mingati.kikunditestrepo.repository;

import com.mingati.kikunditestrepo.base.UserBoRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserBoRole,Long> {
    @Override
    Optional<UserBoRole> findById(Long aLong);
}
