package com.store.blossom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.blossom.dto.UserLoginDto;
import com.store.blossom.dto.UserRegistrationDto;
import com.store.blossom.model.Order;
import com.store.blossom.repository.OrderRepository;
import com.store.blossom.repository.ProductRepository;
import com.store.blossom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

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
    void setUp() {        
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();  
    }

    @Test
    void testRegisterUser() throws Exception {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUsername("francisco");
        dto.setPassword("1234");
        dto.setEmail("francisco@test.com");
        dto.setAddress("Pereira");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("francisco"))
                .andExpect(jsonPath("$.email").value("francisco@test.com"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Primero registrar usuario
        UserRegistrationDto reg = new UserRegistrationDto();
        reg.setUsername("francisco");
        reg.setPassword("1234");
        reg.setEmail("francisco@test.com");
        reg.setAddress("Pereira");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reg)))
                .andExpect(status().isOk());

        // Intentar login con credenciales correctas
        UserLoginDto login = new UserLoginDto();
        login.setUsername("francisco");
        login.setPassword("1234");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("."))); // JWT debe tener un "."
    }

    @Test
    void testLoginFailure() throws Exception {
        UserLoginDto login = new UserLoginDto();
        login.setUsername("noexist");
        login.setPassword("wrong");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized());
    }
}
