package com.products.products.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.products.products.model.Product;
import com.products.products.model.ProductOutput;
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

    public ProductOutput getProductById(String id) {
        Product product = dynamoDBMapper.load(Product.class, id);
        return transformToProductOutput(product);
    }

    private ProductOutput transformToProductOutput(Product product) {
        ProductOutput.ExpenseCategory expenseCategory = getExpenseCategory(product.getPrice());
        String url = String.format("https://example.com/product/%s", product.getId());
        return ProductOutput.builder()
                .product(product)
                .expenseCategory(expenseCategory)
                .url(url)
                .build();
    }

    private ProductOutput.ExpenseCategory getExpenseCategory(Double price) {
        if (price < 10) {
            return ProductOutput.ExpenseCategory.VERY_CHEAP;
        } else if (price < 100) {
            return ProductOutput.ExpenseCategory.CHEAP;
        } else {
            return ProductOutput.ExpenseCategory.EXPENSIVE;
        }
    }
}
