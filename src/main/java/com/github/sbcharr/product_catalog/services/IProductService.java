package com.github.sbcharr.product_catalog.services;

import com.github.sbcharr.product_catalog.models.Product;

import java.util.List;

public interface IProductService {
    Product createProduct(Product product);

    Product updateProduct(Product product, Long id);

    Product getProductById(Long productId);

    List<Product> getAllProducts();

    void deleteProductById(Long id);
}
