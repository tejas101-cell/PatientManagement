package com.pm.authservice.Service;

import com.pm.authservice.DTO.LoginRequestDTO;
import com.pm.authservice.Model.User;
import com.pm.authservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        // checking if the password is matching with the requested user details
        //
        Optional<String> token = userService
                .getByEmail(loginRequestDTO.getEmail())
                .filter(u -> passwordEncoder.mathces(loginRequestDTO.getPassword(),
                        u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));
        return token;
    }
}
