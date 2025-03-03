package com.example.auth_service.externalService;

import com.example.auth_service.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE", path = "api/v1/users")
public interface UserService {
    @PostMapping
    ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto);

    @GetMapping
    ResponseEntity<UserDto> getUser(@RequestParam(value = "email") String email);
}

