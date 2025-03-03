package com.example.auth_service.controller;
import com.example.auth_service.exception.UserDoesNotExistsException;
import com.example.auth_service.externalService.UserService;
import com.example.auth_service.model.AuthRequest;
import com.example.auth_service.model.JwtResponse;
import com.example.auth_service.model.UserDto;
import com.example.auth_service.service.impl.AuthenticationServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> addNewUser(@RequestBody UserDto userDto) {
        UserDto createdUser = authenticationService.register(userDto);
        createdUser.setPassword("******** MASKED DATA **********");
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> getToken(@RequestBody AuthRequest authRequest) {
        UserDto userDto = userService.getUser(authRequest.getUsername()).getBody();
        if(userDto == null){
            throw new UserDoesNotExistsException("Cannot find User");
        }
        JwtResponse jwtResponse = authenticationService.getToken(authRequest, userDto.getRole());
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getRefreshToken(@RequestBody AuthRequest authRequest) {
        UserDto userDto = userService.getUser(authRequest.getUsername()).getBody();
        if(userDto == null){
            throw new UserDoesNotExistsException("Cannot find User");
        }
        JwtResponse jwtResponse = authenticationService.getRefreshToken(authRequest, userDto.getRole());
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }


}
