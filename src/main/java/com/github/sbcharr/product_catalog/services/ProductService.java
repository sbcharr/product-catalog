package com.github.sbcharr.product_catalog.services;

import com.github.sbcharr.product_catalog.dtos.search.SortParams;
import com.github.sbcharr.product_catalog.models.Product;
import com.github.sbcharr.product_catalog.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Slf4j
public class ProductService implements IProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            log.warn("Product with ID {} not found", productId);
            return null;
        }

        return product.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> searchProduct(String query, Integer pageSize, Integer pageNumber, List<SortParams> sortParams)
    {
        Sort sort = null;

        if (sortParams != null && !sortParams.isEmpty()) {
            for (SortParams param : sortParams) {
                Sort newSort = Sort.by(param.getDirection(), param.getField());
                if (sort == null) {
                    sort = newSort;
                } else {
                    sort = sort.and(newSort);
                }
            }
        }

        return productRepository.findByName(query, PageRequest.of(pageNumber, pageSize, sort));
    }
}
