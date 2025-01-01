package com.project.wab.repository;

import com.project.wab.domain.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author "Vladyslav Paun"
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByUserId(Long userId);

    Optional<PasswordResetToken> findByTokenAndExpiryDateAfter(final String token, final LocalDateTime dateTime);

    @Query(value = "select p.user.id from PasswordResetToken p join p.user pu on p.user.id = pu.id where p.token = :token")
    Optional<Long> findUserIByToken(String token);

    void deleteByUserId(final Long userId);

}
