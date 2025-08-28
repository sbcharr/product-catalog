package com.github.sbcharr.product_catalog.services;

import com.github.sbcharr.product_catalog.dtos.search.SortParams;
import com.github.sbcharr.product_catalog.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    Product createProduct(Product product);

    Product updateProduct(Product product, Long id);

    Product getProductById(Long productId);

    List<Product> getAllProducts();

    void deleteProductById(Long id);

    default Page<Product> searchProduct(String query, Integer pageSize, Integer pageNumber, List<SortParams> sortParams) {
        throw new UnsupportedOperationException("Search not implemented");
    }
}
