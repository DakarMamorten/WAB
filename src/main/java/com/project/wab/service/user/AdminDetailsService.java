package com.project.wab.service.user;

import com.project.wab.domain.User;
import com.project.wab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminDetailsService implements UserDetailsService {
    @Value("${lock.time.duration}")
    private long lockTimeDuration;

    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .filter(User::isEnabled)
                .filter(user -> !isUserLocked(user))
                .map(user -> {
                    if (isUserLocked(user)) {
                        log.error("User with login: {} is blocked. Till: {}", email, user.getLockTime().plusMinutes(lockTimeDuration));
                        try {
                            throw new AccountLockedException("User with login: " + email + " : is blocked");
                        } catch (AccountLockedException e) {
                            //todo implement own exception
                            throw new RuntimeException(e);
                        }
                    }
                    return user;
                })
                .orElseThrow(() -> {
                    log.error("User with login: {} not found or not active!", email);
                    return new UsernameNotFoundException("User with login: " + email + " : not found or not active!");
                });
    }

    private boolean isUserLocked(com.project.wab.domain.User user) {
        if (nonNull(user.getLockTime())) {
            return !isTimeExpired(user.getLockTime(), user.getId());
        }
        return false;
    }

    private boolean isTimeExpired(final LocalDateTime lockTime, final Long userId) {
        final LocalDateTime currentTime = LocalDateTime.now();
        final LocalDateTime expirationDate = lockTime.plusMinutes(lockTimeDuration);
        if (expirationDate.isBefore(currentTime)) {
            userRepository.unlockUser(userId);
            return true;
        }
        return false;
    }
}
