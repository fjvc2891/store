package com.store.blossom.controller;

import com.store.blossom.dto.OrderRequestDto;
import com.store.blossom.model.Order;
import com.store.blossom.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Crear orden
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    // Historial de órdenes de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
}
