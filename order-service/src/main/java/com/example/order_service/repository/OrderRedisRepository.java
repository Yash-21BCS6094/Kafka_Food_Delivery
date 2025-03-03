package com.example.order_service.repository;

import com.example.order_service.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRedisRepository {
    public static final String HASH_KEY = "Order";
    @Autowired
    private RedisTemplate<String, Object> template;

    public Order save(Order order){
        template.opsForHash().put(HASH_KEY, order.getId(), order);
        return order;
    }

    public List<Object> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }
    public Order findOrderById(int id){
        return (Order) template.opsForHash().get(HASH_KEY, id);
    }
    public void deleteOrder(int id){
        template.opsForHash().delete(HASH_KEY, id);
    }

}