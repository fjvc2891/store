package com.store.blossom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.blossom.dto.OrderRequestDto;
import com.store.blossom.dto.UserRegistrationDto;
import com.store.blossom.dto.UserLoginDto;
import com.store.blossom.model.Product;
import com.store.blossom.repository.ProductRepository;
import com.store.blossom.repository.UserRepository;
import com.store.blossom.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;    

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        // Limpiar completamente la base
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();        

        // Registrar usuario de prueba
        UserRegistrationDto reg = new UserRegistrationDto();
        reg.setUsername("francisco");
        reg.setPassword("1234");
        reg.setEmail("francisco@test.com");
        reg.setAddress("Pereira");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reg)))
                .andExpect(status().isOk());

        // Crear productos de prueba
        productRepository.save(new Product(null, "Laptop", "Electronics", 1200.0, null));
        productRepository.save(new Product(null, "Mouse", "Electronics", 25.0, null));
    }

    @Test
    void testCreateOrder() throws Exception {
        Long userId = userRepository.findAll().get(0).getId();
        List<Long> productIds = productRepository.findAll().stream()
                .map(Product::getId)
                .toList();

        OrderRequestDto order = new OrderRequestDto();
        order.setUserId(userId);
        order.setProductIds(productIds);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].name").value("Laptop"))
                .andExpect(jsonPath("$.products[1].name").value("Mouse"));
    }

    @Test
    void testGetOrdersByUser() throws Exception {
        // Crear orden primero
        testCreateOrder();

        Long userId = userRepository.findAll().get(0).getId();

        mockMvc.perform(get("/api/orders/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].products[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].products[1].name").value("Mouse"));
    }
}
