package com.bookstore.controller;

import com.bookstore.dto.user.request.UserLoginRequestDto;
import com.bookstore.dto.user.request.UserRegistrationRequestDto;
import com.bookstore.dto.user.response.UserLoginResponseDto;
import com.bookstore.dto.user.response.UserResponseDto;
import com.bookstore.exception.RegistrationException;
import com.bookstore.security.AuthenticationService;
import com.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registration a new user", description = "Registration a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New User registered successfully",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = UserResponseDto.class))
                    })
    })
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
