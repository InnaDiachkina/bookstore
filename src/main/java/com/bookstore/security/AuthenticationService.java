package com.bookstore.security;

import com.bookstore.dto.shoppingcart.ShoppingCartRequestDto;
import com.bookstore.dto.user.request.UserLoginRequestDto;
import com.bookstore.dto.user.request.UserRegistrationRequestDto;
import com.bookstore.dto.user.response.UserLoginResponseDto;
import com.bookstore.dto.user.response.UserResponseDto;
import com.bookstore.exception.UserRegistrationException;
import com.bookstore.mapper.UserMapper;
import com.bookstore.model.Role;
import com.bookstore.model.User;
import com.bookstore.repository.user.RoleRepository;
import com.bookstore.repository.user.UserRepository;
import com.bookstore.service.ShoppingCartService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                        requestDto.getPassword())
        );
        String token = jwtUtil.generateToken(requestDto.getEmail());
        UserLoginResponseDto responseDto = new UserLoginResponseDto();
        responseDto.setToken(token);
        return responseDto;
    }

    public UserResponseDto register(UserRegistrationRequestDto request)
            throws UserRegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserRegistrationException("Unable to complete registration.");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getEmail().equals("admin.example@example.com")) {
            Role adminRole = roleRepository.findByName(Role.RoleName.ADMIN)
                    .orElseThrow(() -> new UserRegistrationException("Default role not found."));
            user.setRoles(Set.of(adminRole));
        } else {
            Role userRole = roleRepository.findByName(Role.RoleName.USER)
                    .orElseThrow(() -> new UserRegistrationException("Default role not found."));
            user.setRoles(Set.of(userRole));
        }
        User savedUser = userRepository.save(user);
        UserResponseDto responseDto = userMapper.toDto(userRepository.save(savedUser));
        ShoppingCartRequestDto shoppingCart = new ShoppingCartRequestDto();
        shoppingCart.setUserId(responseDto.getId());
        Set<Long> cartItemIds = new HashSet<>();
        shoppingCart.setCartItemIds(cartItemIds);
        shoppingCartService.save(shoppingCart);
        return userMapper.toDto(userRepository.save(savedUser));
    }
}
