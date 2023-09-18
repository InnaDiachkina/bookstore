package com.bookstore.service.impl;

import com.bookstore.dto.user.request.UserRegistrationRequestDto;
import com.bookstore.dto.user.response.UserResponseDto;
import com.bookstore.exception.RegistrationException;
import com.bookstore.mapper.UserMapper;
import com.bookstore.model.Role;
import com.bookstore.model.User;
import com.bookstore.repository.user.RoleRepository;
import com.bookstore.repository.user.UserRepository;
import com.bookstore.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration.");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getEmail().equals("admin.example@example.com")) {
            Role adminRole = roleRepository.findByName(Role.RoleName.ADMIN)
                    .orElseThrow(() -> new RegistrationException("Default role not found."));
            user.setRoles(Set.of(adminRole));
        } else {
            Role userRole = roleRepository.findByName(Role.RoleName.USER)
                    .orElseThrow(() -> new RegistrationException("Default role not found."));
            user.setRoles(Set.of(userRole));
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDto(userRepository.save(savedUser));
    }
}
