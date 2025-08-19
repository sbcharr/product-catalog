package com.github.sbcharr.product_catalog.services;

import com.github.sbcharr.product_catalog.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long productId);
    List<Product> getAllProducts();
    Product createProduct(Product product);
}
