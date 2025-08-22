package com.github.sbcharr.product_catalog.services;

import com.github.sbcharr.product_catalog.models.Product;
import com.github.sbcharr.product_catalog.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Slf4j
public class ProductService implements IProductService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        product.setId(id);
        return productRepo.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> product = productRepo.findById(productId);
        if (product.isEmpty()) {
            log.warn("Product with ID {} not found", productId);
            return null;
        }

        return product.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public void deleteProductById(Long id) {
        productRepo.deleteById(id);
    }
}
