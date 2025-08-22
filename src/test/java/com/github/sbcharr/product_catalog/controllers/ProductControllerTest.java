package com.github.sbcharr.product_catalog.controllers;

import com.github.sbcharr.product_catalog.dtos.request.ProductRequestDto;
import com.github.sbcharr.product_catalog.dtos.response.ProductResponseDto;
import com.github.sbcharr.product_catalog.models.Category;
import com.github.sbcharr.product_catalog.models.Product;
import com.github.sbcharr.product_catalog.services.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @InjectMocks
    private ProductController productController;
    @Mock
    private IProductService productService;

    private Product sampleProduct;
    private ProductRequestDto sampleProductRequestDto;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("iPhone");
        sampleProduct.setDescription("Latest iPhone");
        sampleProduct.setPrice(999.99);
        sampleProduct.setImageUrl("http://example.com/image.jpg");
        Category sampleCategory = new Category();
        sampleCategory.setId(1L);
        sampleCategory.setName("Electronics");
        sampleProduct.setCategory(sampleCategory);

        sampleProductRequestDto = new ProductRequestDto();
        sampleProductRequestDto.setName("iPhone");
        sampleProductRequestDto.setDescription("Latest iPhone");
        sampleProductRequestDto.setPrice(999.99);
        sampleProductRequestDto.setImageurl("http://image");

        Category sampleCategoryDto = new Category();
        sampleCategoryDto.setId(1L);
        sampleCategoryDto.setName("Electronics");
        sampleProduct.setCategory(sampleCategory);
    }

    @Test
    public void testCreateProduct_success() {
        when(productService.createProduct(any(Product.class))).thenReturn(sampleProduct);
        ProductResponseDto responseDto = productController.createProduct(sampleProductRequestDto);
        assertNotNull(responseDto);
        assertEquals(sampleProduct.getId(), responseDto.getId());
        assertEquals(sampleProduct.getName(), responseDto.getName());
        assertEquals(sampleProduct.getDescription(), responseDto.getDescription());
        assertEquals(sampleProduct.getPrice(), responseDto.getPrice());
        assertEquals(sampleProduct.getImageUrl(), responseDto.getImageurl());
        //assertEquals(sampleProduct.getCategory(), responseDto.getCategory());
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void createProduct_failure_returns500() {
        when(productService.createProduct(any(Product.class))).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> productController.createProduct(sampleProductRequestDto));

        assertEquals(500, ex.getStatusCode().value());
    }

    @Test
    void updateProduct_success() {
        when(productService.updateProduct(any(Product.class), eq(1L))).thenReturn(sampleProduct);

        ProductResponseDto response = productController.updateProduct(sampleProductRequestDto, 1L);

        assertNotNull(response);
        assertEquals("iPhone", response.getName());
        verify(productService).updateProduct(any(Product.class), eq(1L));
    }

    @Test
    void updateProduct_invalidId_throws400() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> productController.updateProduct(sampleProductRequestDto, 0L));

        assertEquals(400, ex.getStatusCode().value());
        verify(productService, never()).updateProduct(any(), any());
    }

    @Test
    void updateProduct_failure_returns400() {
        when(productService.updateProduct(any(Product.class), eq(1L))).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> productController.updateProduct(sampleProductRequestDto, 1L));

        assertEquals(400, ex.getStatusCode().value());
    }

    @Test
    void getAllProducts_success() {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(sampleProduct));

        List<ProductResponseDto> response = productController.getAllProducts();

        assertEquals(1, response.size());
        assertEquals("iPhone", response.get(0).getName());
        verify(productService).getAllProducts();
    }

    @Test
    void getAllProducts_emptyList() {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        List<ProductResponseDto> response = productController.getAllProducts();

        assertTrue(response.isEmpty());
    }

    @Test
    void getProductById_success() {
        when(productService.getProductById(1L)).thenReturn(sampleProduct);

        ProductResponseDto response = productController.getProductById(1L);

        assertNotNull(response);
        assertEquals("iPhone", response.getName());
        verify(productService).getProductById(1L);
    }

    @Test
    void getProductById_notFound_throws404() {
        when(productService.getProductById(1L)).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> productController.getProductById(1L));

        assertEquals(404, ex.getStatusCode().value());
    }

    @Test
    void deleteProductById_success() {
        doNothing().when(productService).deleteProductById(1L);

        ProductResponseDto response = productController.deleteProductById(1L);

        assertNotNull(response);
        verify(productService).deleteProductById(1L);
    }
}
