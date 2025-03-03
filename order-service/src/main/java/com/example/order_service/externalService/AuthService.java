package com.example.order_service.externalService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "AUTH-SERVICE")
//public interface AuthService {
//    @GetMapping("/api/v1/auth/get-role")
//    ResponseEntity<String> getRole(@RequestParam("username") String username);
//}
