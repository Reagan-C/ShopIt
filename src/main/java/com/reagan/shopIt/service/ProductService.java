package com.reagan.shopIt.service;

import com.reagan.shopIt.model.dto.ProductDTO;

public interface ProductService {
    ProductDTO getProductById(Long productId);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    // Add more methods for product-related operations
}
