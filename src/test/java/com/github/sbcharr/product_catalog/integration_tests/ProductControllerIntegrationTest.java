package com.github.sbcharr.product_catalog.integration_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbcharr.product_catalog.ProductCatalogApplication;
import com.github.sbcharr.product_catalog.models.Product;
import com.github.sbcharr.product_catalog.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProductCatalogApplication.class)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    private Product existingProduct;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
        existingProduct = new Product();
        existingProduct.setName("Laptop");
        existingProduct.setDescription("MSI Laptop");
        existingProduct.setPrice(1999.99);
        existingProduct.setImageUrl("https://example.com/existing.jpg");
        existingProduct = productRepository.save(existingProduct);
    }

    @Test
    void shouldGetProductById() throws Exception {
        mockMvc.perform(get("/products/{id}", existingProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingProduct.getId()))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.description").value("MSI Laptop"))
                .andExpect(jsonPath("$.price").value(1999.99))
                .andExpect(jsonPath("$.imageUrl").value("https://example.com/existing.jpg"));
    }
}
