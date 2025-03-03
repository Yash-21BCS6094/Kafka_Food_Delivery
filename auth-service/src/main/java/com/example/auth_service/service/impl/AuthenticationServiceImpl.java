package com.example.auth_service.service.impl;

import com.example.auth_service.exception.AuthFeignClientException;
import com.example.auth_service.exception.UserDoesNotExistsException;
import com.example.auth_service.externalService.UserService;
import com.example.auth_service.model.AuthRequest;
import com.example.auth_service.model.JwtResponse;
import com.example.auth_service.model.UserDto;
import com.example.auth_service.service.AuthenticationService;
import com.example.auth_service.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthenticationService interface.
 * This service handles user registration, authentication, and token generation.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * Constructor for AuthenticationServiceImpl.
     *
     * @param userService Service to interact with the User Service via Feign Client.
     * @param jwtService Service for JWT token generation.
     */
    @Autowired
    public AuthenticationServiceImpl(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user by making a call to the User Service.
     *
     * @param userDto User details to be registered.
     * @return The registered UserDto object.
     * @throws AuthFeignClientException if there is an issue with the Feign client.
     */
    @Override
    public UserDto register(UserDto userDto) {
        try {
            ResponseEntity<UserDto> response = userService.createUser(userDto);
            if (response != null && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new AuthFeignClientException("Feign Client returned null response!");
            }
        } catch (Exception ex) {
            throw new AuthFeignClientException("Feign Client not working! " + ex.getMessage());
        }
    }

    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param authRequest Contains the username and password of the user.
     * @param role The role of the user (e.g., ADMIN, USER).
     * @return A JwtResponse containing the generated token.
     * @throws UserDoesNotExistsException if the user is not found in the User Service.
     */
    @Override
    public JwtResponse getToken(AuthRequest authRequest, String role) {
        String token = jwtService.generateToken(authRequest.getUsername(), role);

        if (userService.getUser(authRequest.getUsername()) != null) {
            JwtResponse response = new JwtResponse();
            response.setToken(token);
            return response;
        } else {
            throw new UserDoesNotExistsException("Cannot find User");
        }
    }

    /**
     * Generates a new JWT refresh token.
     * This method simply calls getToken() to generate a new token.
     * @param authRequest Contains the username and password of the user.
     * @param role The role of the user.
     * @return A JwtResponse containing the refreshed token.
     */

    @Override
    public JwtResponse getRefreshToken(AuthRequest authRequest, String role) {
        return getToken(authRequest, role);
    }

}
