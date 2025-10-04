package com.store.blossom.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDto {
    private Long userId;           // Usuario que hace la orden
    private List<Long> productIds; // Productos que quiere comprar
}
