package com.store.blossom.repository;

import com.store.blossom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar productos que contengan un texto en el nombre (ignore case)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Buscar productos por categoría
    List<Product> findByCategory(String category);

    // Buscar productos en un rango de precios
    List<Product> findByPriceBetween(Double min, Double max);
}
