package com.chaegangjo.product.application;

import com.chaegangjo.product.dto.ProductInfo;
import com.chaegangjo.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SearchProductUseCase {

    private final ProductService productService;

    @Cacheable(value = "product-autocomplete", key = "#keyword")
    public List<ProductInfo> execute(String keyword) {
        return productService.searchByKeyword(keyword)
                .stream()
                .map(ProductInfo::from)
                .toList();
    }
}
