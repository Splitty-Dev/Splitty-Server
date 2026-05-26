package com.chaegangjo.product.service;

import com.chaegangjo.product.domain.Product;
import com.chaegangjo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> searchByKeyword(String keyword) {
        return productRepository.findTop7ByNameContainingIgnoreCase(keyword);
    }
}
