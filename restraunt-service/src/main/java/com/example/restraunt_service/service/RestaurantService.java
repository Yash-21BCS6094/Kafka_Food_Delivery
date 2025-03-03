package com.example.restraunt_service.service;

import com.example.restraunt_service.dto.RestaurantDto;
import com.example.restraunt_service.dto.RestaurantRequest;
import com.example.restraunt_service.dto.RestaurantResponse;
import com.example.restraunt_service.model.Restaurant;
import com.example.restraunt_service.repository.FoodItemRepository;
import com.example.restraunt_service.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, FoodItemRepository foodItemRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository = foodItemRepository;
        this.modelMapper = modelMapper;
    }

    public String addRestaurant(RestaurantRequest request) {
        Restaurant restaurant = modelMapper.map(request, Restaurant.class);
        restaurantRepository.save(restaurant);
        return "Restaurant with id: " + restaurant.getId() + " added successfully";
    }

    public RestaurantResponse getRestaurant(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        if(restaurant.isPresent()) {
            restaurantResponse.setRestaurantDto(modelMapper.map(restaurant.get(), RestaurantDto.class));
            restaurantResponse.setResponseCode(200);
            restaurantResponse.setMsg("Response sent successfully");
            return restaurantResponse;
        }else{
            throw new RuntimeException("Cannot find requested restaurant");
        }
    }

    public List<RestaurantDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDto> restaurantDtos = restaurants.stream().map(
                (restaurant -> modelMapper.map(restaurant, RestaurantDto.class)
        )).toList();
        return restaurantDtos;
    }

    public RestaurantDto getRandomRestaurant(){
        Restaurant restaurant = restaurantRepository.findRandomRestaurant();
        return modelMapper.map(restaurant, RestaurantDto.class);
    }

}
