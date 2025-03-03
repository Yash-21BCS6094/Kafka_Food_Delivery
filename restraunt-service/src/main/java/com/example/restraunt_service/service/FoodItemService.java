package com.example.restraunt_service.service;

import com.example.restraunt_service.dto.FoodItemDto;
import com.example.restraunt_service.model.FoodItem;
import com.example.restraunt_service.model.Restaurant;
import com.example.restraunt_service.repository.FoodItemRepository;
import com.example.restraunt_service.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FoodItemService(FoodItemRepository foodItemRepository, RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.foodItemRepository = foodItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }


    public FoodItemDto addFoodItem(FoodItemDto foodItemDto) {
        Restaurant restaurant = restaurantRepository.findById(foodItemDto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + foodItemDto.getRestaurantId()));
        FoodItem foodItem = modelMapper.map(foodItemDto, FoodItem.class);
        foodItem.setRestaurant(restaurant);
        FoodItem savedFoodItem = foodItemRepository.save(foodItem);
        return modelMapper.map(savedFoodItem, FoodItemDto.class);
    }

    public List<FoodItemDto> getAllFoodItems(Long restaurantId) {
        List<FoodItem> allFoodItems =  foodItemRepository.findByRestaurantId(restaurantId);
        List<FoodItemDto> allFoodItemDto = allFoodItems.stream().map(
                (foodItem -> modelMapper.map(foodItem, FoodItemDto.class))
        ).toList();
        return allFoodItemDto;
    }


    public void update(Long id, FoodItemDto foodItemDto) {
        FoodItem savedItem = foodItemRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cannot find item")
        );
        FoodItem itemUpdated = modelMapper.map(foodItemDto, FoodItem.class);
        foodItemRepository.save(itemUpdated);
    }

}