package com.bookstore.mapper;

import com.bookstore.config.MapperConfig;
import com.bookstore.dto.user.request.UserRegistrationRequestDto;
import com.bookstore.dto.user.response.UserResponseDto;
import com.bookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto userRequestDto);
}
