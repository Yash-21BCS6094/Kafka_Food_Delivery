package com.example.restraunt_service.repository;

import com.example.restraunt_service.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT r FROM Restaurant r ORDER BY FUNCTION('RAND') LIMIT 1")
    Restaurant findRandomRestaurant();
}
