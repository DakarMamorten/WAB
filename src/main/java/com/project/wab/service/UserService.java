package com.project.wab.service;

import com.project.wab.UserRegisterDto;
import com.project.wab.domain.PasswordResetToken;
import com.project.wab.domain.Role;
import com.project.wab.domain.User;
import com.project.wab.exception.UserNotFoundException;
import com.project.wab.listener.PasswordResetEvent;
import com.project.wab.repository.PasswordResetTokenRepository;
import com.project.wab.repository.RoleRepository;
import com.project.wab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return StreamSupport
                .stream(users.spliterator(), false)
                .toList();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void registerUser(UserRegisterDto dto) {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setFirstName(dto.name());
        user.setLastName(dto.surname());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setRole(userRole);
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public boolean userIsExist(String username) {
        return userRepository.findByEmail(username.trim()).isPresent();
    }

    @Transactional
    public void createPasswordResetTokenForUser(String email) {
        var user = findByEmail(email);

        var tokenFromDb = passwordResetTokenRepository
                .findByUserId(user.getId())
                .orElseGet(
                        () -> passwordResetTokenRepository.save(new PasswordResetToken(user))
                );

        eventPublisher.publishEvent(new PasswordResetEvent(this, email, tokenFromDb.getToken(), user.getFullName()));

    }

    public void changePassword(Long id, String newPassword) {
        var user = findById(id);
        user.setPassword(new BCryptPasswordEncoder(10).encode(newPassword));
        userRepository.save(user);
    }
}

