package com.project.wab.repository;

import com.project.wab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author "Vladyslav Paun"
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.failedAttempt = 0, u.lockTime = null, u.accountNonLocked = true WHERE u.id = :userId")
    void unlockUser(Long userId);
}
