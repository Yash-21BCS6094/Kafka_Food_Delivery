package com.example.restraunt_service.controller;

import com.example.restraunt_service.dto.RestaurantDto;
import com.example.restraunt_service.dto.RestaurantRequest;
import com.example.restraunt_service.dto.RestaurantResponse;
import com.example.restraunt_service.service.RestaurantService;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<String> addRestaurant(@RequestBody RestaurantRequest request) {
        String response = restaurantService.addRestaurant(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> restaurantDtoList = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurantDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurant(@PathVariable("id") Long id) {
        RestaurantResponse response = restaurantService.getRestaurant(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<RestaurantDto> getRandomRestaurant(){
        RestaurantDto restaurantDto = restaurantService.getRandomRestaurant();
        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }
}
