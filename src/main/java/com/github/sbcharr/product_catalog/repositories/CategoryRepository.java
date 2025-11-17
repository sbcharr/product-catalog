package com.github.sbcharr.product_catalog.repositories;

import com.github.sbcharr.product_catalog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Override
    void deleteById(Long categoryId);
}
