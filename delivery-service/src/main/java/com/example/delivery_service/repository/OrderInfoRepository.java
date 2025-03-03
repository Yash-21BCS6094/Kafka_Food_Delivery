package com.example.delivery_service.repository;

import com.example.delivery_service.model.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, UUID> {
}
