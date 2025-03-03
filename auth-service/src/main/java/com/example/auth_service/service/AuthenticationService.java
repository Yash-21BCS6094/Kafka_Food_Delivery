package com.example.auth_service.service;
import com.example.auth_service.model.AuthRequest;
import com.example.auth_service.model.JwtDto;
import com.example.auth_service.model.JwtResponse;
import com.example.auth_service.model.UserDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    public UserDto register(UserDto userDto);
    public JwtResponse getToken(AuthRequest authRequest, String role);
    public JwtResponse getRefreshToken(AuthRequest authRequest, String role);
}
