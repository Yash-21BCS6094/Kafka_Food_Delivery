package com.example.restraunt_service.controller;

import com.example.restraunt_service.dto.FoodItemDto;
import com.example.restraunt_service.service.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/food-item")
@RequiredArgsConstructor
public class FoodItemController {
    @Autowired
    private FoodItemService foodItemService;

    @PostMapping
    public ResponseEntity<FoodItemDto> addFoodItem(@RequestBody FoodItemDto foodItemDto) {
        FoodItemDto data = foodItemService.addFoodItem(foodItemDto);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<FoodItemDto>> getAllFoodItems(@PathVariable("restaurantId") Long restaurantId) {
        List<FoodItemDto> foodItemDtoList = foodItemService.getAllFoodItems(restaurantId);
        return new ResponseEntity<>(foodItemDtoList, HttpStatus.OK);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateFoodItem(@RequestBody FoodItemDto foodItemDto,
                                                 @PathVariable("itemId") Long itemId) {
        foodItemService.update(itemId, foodItemDto);
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
    }

}
