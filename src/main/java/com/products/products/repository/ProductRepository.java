package com.products.products.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.products.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public ProductRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void addOrUpdateProduct(Product product) {
        dynamoDBMapper.save(product);
    }

    public Product getProductById(String id) {
        return dynamoDBMapper.load(Product.class, id);
    }
}
