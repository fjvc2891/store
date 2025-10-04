package com.store.blossom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.blossom.model.Product;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();  
    }


    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product(null, "Test GPU", "Graphics", 999.0);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test GPU"));
    }

    @Test
    void testGetProducts() throws Exception {
        productRepository.save(new Product(null, "Keyboard", "Peripherals", 50.0));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Keyboard"));
    }

    @Test
    void testGetProductById() throws Exception {
        Product saved = productRepository.save(new Product(null, "Monitor", "Electronics", 200.0));

        mockMvc.perform(get("/api/products/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Monitor"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product saved = productRepository.save(new Product(null, "Old Mouse", "Electronics", 10.0));
        Product updated = new Product(null, "New Mouse", "Electronics", 15.0);

        mockMvc.perform(put("/api/products/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Mouse"))
                .andExpect(jsonPath("$.price").value(15.0));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Product saved = productRepository.save(new Product(null, "To Delete", "Electronics", 50.0));

        mockMvc.perform(delete("/api/products/" + saved.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchByName() throws Exception {
        productRepository.save(new Product(null, "Gaming Laptop", "Electronics", 1200.0));

        mockMvc.perform(get("/api/products/search")
                .param("name", "Laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gaming Laptop"));
    }

    @Test
    void testSearchByCategory() throws Exception {
        productRepository.save(new Product(null, "Office Chair", "Furniture", 100.0));

        mockMvc.perform(get("/api/products/search")
                .param("category", "Furniture"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Office Chair"));
    }

    @Test
    void testSearchByPriceRange() throws Exception {
        productRepository.save(new Product(null, "Cheap Desk", "Furniture", 50.0));
        productRepository.save(new Product(null, "Expensive Desk", "Furniture", 500.0));

        mockMvc.perform(get("/api/products/search")
                .param("min", "40")
                .param("max", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cheap Desk"));
    }
}
