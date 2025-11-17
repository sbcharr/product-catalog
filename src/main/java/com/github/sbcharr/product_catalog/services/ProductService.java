package com.github.sbcharr.product_catalog.services;

import com.github.sbcharr.product_catalog.clients.FakeStoreApiClient;
import com.github.sbcharr.product_catalog.dtos.search.SortParams;
import com.github.sbcharr.product_catalog.models.Product;
import com.github.sbcharr.product_catalog.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public ProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
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
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + productId);
        if (product == null) {
            // cache HIT
            return product;
        }
        // cache MISS
        log.info("cache miss for product id={}", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            log.warn("Product with ID {} not found", productId);
            return null;
        } else {
            // Store in cache
            redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + productId, product.get());
        }

        return optionalProduct.get();
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

        return productRepository.findByNameContainingIgnoreCase(query, PageRequest.of(pageNumber, pageSize, sort));
    }
}
