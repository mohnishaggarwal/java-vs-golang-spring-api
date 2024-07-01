package com.products.products.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.products.products.model.Product;
import com.products.products.model.ProductOutput;
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody Product product) {
        product.setId(id);
        productRepository.addOrUpdateProduct(product);
        return ResponseEntity.ok("Product updated successfully");
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        productRepository.addOrUpdateProduct(product);
        return ResponseEntity.ok("Product added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOutput> getProductById(@PathVariable String id) {
        ProductOutput product = productRepository.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
