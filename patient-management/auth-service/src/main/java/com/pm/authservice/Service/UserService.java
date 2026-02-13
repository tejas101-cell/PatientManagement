package com.pm.authservice.Service;

import com.pm.authservice.Model.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> getByEmail(String email);
}
