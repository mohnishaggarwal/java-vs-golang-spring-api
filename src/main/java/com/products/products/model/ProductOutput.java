package com.products.products.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductOutput {
    private Product product;
    private String url;
    private ExpenseCategory expenseCategory;

    public enum ExpenseCategory {
        VERY_CHEAP,
        CHEAP,
        EXPENSIVE
    }
}
