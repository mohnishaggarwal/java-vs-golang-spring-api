package com.products.products.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.products.products.model.Product;
import com.products.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final AmazonDynamoDB amazonDynamoDB;

    @Autowired
    public ProductController(ProductRepository productRepository, AmazonDynamoDB amazonDynamoDB) {
        this.productRepository = productRepository;
        this.amazonDynamoDB = amazonDynamoDB;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        productRepository.updateProduct(product);
        return ResponseEntity.ok("Product updated successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productRepository.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
