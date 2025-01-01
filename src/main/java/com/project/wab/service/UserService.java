package com.project.wab.service;

import com.project.wab.UserRegisterDto;
import com.project.wab.domain.Address;
import com.project.wab.domain.Role;
import com.project.wab.domain.User;
import com.project.wab.dto.AddressDTO;
import com.project.wab.exception.UserNotFoundException;
import com.project.wab.mapper.AddressMapper;
import com.project.wab.repository.RoleRepository;
import com.project.wab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    private final AddressMapper addressMapper;
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

    public User getReferenceById(Long userId) {
        return userRepository.getReferenceById(userId);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public AddressDTO getAddressByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return addressMapper.AddressToAddressDto(user.getAddress());
    }

    @Transactional
    public void updateAddress(AddressDTO addressDTO) {
        User user = userRepository.findByEmail(addressDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + addressDTO.getEmail()));
        Address address = user.getAddress();
        addressMapper.updateAddressFromDto(address, addressDTO);

        userRepository.save(user);
    }
}

