package com.example.productservice.service;

import com.example.productservice.dto.CreateProductRequest;
import com.example.productservice.dto.ProductPageResponse;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.dto.ProductV1Response;
import com.example.productservice.dto.ProductV2Response;
import com.example.productservice.dto.UpdateProductRequest;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.DuplicateProductException;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request) {

        if (productRepository.existsByName(request.getName())) {
            throw new DuplicateProductException("Product already exists");
        }

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());
        product.setStatus("ACTIVE");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    public ProductPageResponse getAllProducts(Pageable pageable) {

        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> productList = productPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return new ProductPageResponse(
                productList,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );
    }

    public List<ProductV1Response> getAllProductsV1() {

        return productRepository.findAll()
                .stream()
                .map(product -> new ProductV1Response(
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                ))
                .toList();
    }

    public ProductResponse getProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        return toResponse(product);
    }

    public ProductResponse updateProduct(
            Long id,
            UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        return toResponse(updatedProduct);
    }
    public ProductResponse patchProduct(
            Long id,
            UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getCategory() != null) {
            product.setCategory(request.getCategory());
        }

        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }

        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        return toResponse(updatedProduct);
    }
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    public List<ProductV2Response> getAllProductsV2() {

        return productRepository.findAll()
                .stream()
                .map(product -> new ProductV2Response(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getStock(),
                        product.getStatus()
                ))
                .toList();
    }
    private ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory());
        response.setStock(product.getStock());
        response.setStatus(product.getStatus());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }
}