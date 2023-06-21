//package com.reagan.shopIt.service.ServiceImpl;
//
//import com.reagan.shopIt.model.domain.Products;
//import com.reagan.shopIt.model.exception.ProductNotFoundException;
//import com.reagan.shopIt.repository.ProductRepository;
//import com.reagan.shopIt.service.ProductService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//    private final ProductRepository productRepository;
//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
//        this.productRepository = productRepository;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public ProductDTO getProductById(Long productId) {
//        Products products = productRepository.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
//        return modelMapper.map(products, ProductDTO.class);
//    }
//
//    @Override
//    public ProductDTO createProduct(ProductDTO productDTO) {
//        Products products = modelMapper.map(productDTO, Products.class);
//        Products savedProducts = productRepository.save(products);
//        return modelMapper.map(savedProducts, ProductDTO.class);
//    }
//
//    @Override
//    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
//        return null;
//    }
//
//    @Override
//    public void deleteProduct(Long id) {
//
//    }
//
//    // Implement other methods from the ProductService interface
//}