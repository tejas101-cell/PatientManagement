package com.pm.authservice.Service;


import com.pm.authservice.DTO.LoginRequestDTO;

import java.util.Optional;

public interface AuthService {
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO);
}
