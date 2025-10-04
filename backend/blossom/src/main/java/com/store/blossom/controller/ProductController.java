package com.store.blossom.controller;

import com.store.blossom.model.Product;
import com.store.blossom.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Crear producto
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setCategory(updatedProduct.getCategory());
                    product.setPrice(updatedProduct.getPrice());
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔎 Buscar productos con filtros opcionales
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max) {

        List<Product> results;

        if (name != null && !name.isEmpty()) {
            results = productRepository.findByNameContainingIgnoreCase(name);
        } else if (category != null && !category.isEmpty()) {
            results = productRepository.findByCategory(category);
        } else if (min != null && max != null) {
            results = productRepository.findByPriceBetween(min, max);
        } else {
            results = productRepository.findAll(); // sin filtros
        }

        return ResponseEntity.ok(results);
    }
}
