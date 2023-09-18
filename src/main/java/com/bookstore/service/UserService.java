package com.bookstore.service;

import com.bookstore.dto.user.request.UserRegistrationRequestDto;
import com.bookstore.dto.user.response.UserResponseDto;
import com.bookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
