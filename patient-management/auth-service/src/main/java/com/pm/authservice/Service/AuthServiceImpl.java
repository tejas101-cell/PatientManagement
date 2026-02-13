package com.pm.authservice.Service;

import com.pm.authservice.DTO.LoginRequestDTO;
import com.pm.authservice.Model.User;
import com.pm.authservice.Repository.UserRepository;
import com.pm.authservice.Util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        // checking if the password is matching with the requested user details
        //
        Optional<String> token = userService
                .getByEmail(loginRequestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(),
                        u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));
        return token;
    }

    @Override
    public boolean validateToken(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }
}
