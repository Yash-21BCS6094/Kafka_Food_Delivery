package com.example.order_service.externalService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductService {
    @GetMapping("/api/v1/products/get-all")
    List<UUID> getAllProductIds();
}

