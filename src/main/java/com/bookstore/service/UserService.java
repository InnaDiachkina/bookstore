package com.bookstore.service;

import com.bookstore.model.User;

public interface UserService {
    User findByEmail(String email);
}
