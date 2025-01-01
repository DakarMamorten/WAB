package com.project.wab.service;

import com.project.wab.exception.UserNotFoundException;
import com.project.wab.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository resetTokenRepository;

    public boolean validate(String token) {
        return resetTokenRepository.findByTokenAndExpiryDateAfter(token, LocalDateTime.now()).isPresent();
    }

    public Long getUserIdByToken(String token) {
        return resetTokenRepository.findUserIByToken(token).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    @Modifying
    public void deleteToken(Long userId) {
        resetTokenRepository.deleteByUserId(userId);
    }
}
