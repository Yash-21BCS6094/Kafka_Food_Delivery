package com.example.order_service.controller;

import com.example.order_service.exception.InvalidAccessException;
//import com.example.order_service.externalService.AuthService;
import com.example.order_service.model.dto.OrderDto;
import com.example.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST: Create a new order
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.CREATED);
    }

    // GET: Retrieve an order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @GetMapping
    public Page<OrderDto> getOrders(
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (orderStatus != null) {
            return orderService.getAllOrderByStatus(pageable, orderStatus);
        }

        if (customerId != null) {
            List<OrderDto> orders = orderService.getOrderByCustomerId(customerId);
            return new org.springframework.data.domain.PageImpl<>(orders, pageable, orders.size());
        }

        return orderService.getAllOrder(pageable);
    }

    // PUT: Update the status of an existing order
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable UUID orderId,
                                                      @RequestParam String status) {
        return new ResponseEntity<>(orderService.updateOrderStatus(orderId, status), HttpStatus.OK);
    }

    // DELETE: Remove an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Order Deleted Successfully", HttpStatus.NO_CONTENT);
    }

}
