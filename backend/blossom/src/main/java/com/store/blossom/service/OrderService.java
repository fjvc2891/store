package com.store.blossom.service;

import com.store.blossom.dto.OrderRequestDto;
import com.store.blossom.model.Order;
import com.store.blossom.model.Product;
import com.store.blossom.model.User;
import com.store.blossom.repository.OrderRepository;
import com.store.blossom.repository.ProductRepository;
import com.store.blossom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Crear orden
    public Order createOrder(OrderRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Product> products = productRepository.findAllById(dto.getProductIds());

        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
        orderRepository.save(order);

        order.setProducts(productRepository.findAllById(dto.getProductIds()));

        return order;
    }

    // Historial de órdenes de un usuario
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
