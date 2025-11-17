package com.github.sbcharr.product_catalog.repositories;

import com.github.sbcharr.product_catalog.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String query, Pageable pageable);

    List<Product> findByPriceBetween(Double priceLow, Double priceHigh);

    @Query("SELECT p.name FROM Product p WHERE p.id = :id")
    String findNameById(Long id);
}
