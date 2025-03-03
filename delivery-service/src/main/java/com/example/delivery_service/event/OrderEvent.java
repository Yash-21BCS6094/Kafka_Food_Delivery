package com.example.delivery_service.event;
import java.util.UUID;

public class OrderEvent {
    public UUID userId;
    public UUID orderId;
    public String status;

    public OrderEvent() {
    }

    public OrderEvent(UUID userId, UUID orderId, String status) {
        this.userId = userId;
        this.orderId = orderId;
        this.status = status;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}