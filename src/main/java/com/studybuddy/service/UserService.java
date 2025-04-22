package com.studybuddy.service;

import com.studybuddy.model.User;
import java.util.Optional;

public interface UserService {
    User register(String email, String password);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
