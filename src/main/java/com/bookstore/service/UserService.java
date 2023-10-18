package com.bookstore.service;

import com.bookstore.model.User;
import org.mapstruct.Named;

public interface UserService {
    User findByEmail(String email);

    @Named("FindUserById")
    User findById(Long id);
}
