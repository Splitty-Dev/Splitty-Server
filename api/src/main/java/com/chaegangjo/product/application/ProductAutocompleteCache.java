package com.chaegangjo.product.application;

import com.chaegangjo.product.dto.CachedProductInfo;
import com.chaegangjo.product.dto.ProductInfo;
import com.chaegangjo.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class ProductAutocompleteCache {

    private static final String ALL_PRODUCTS_KEY = "'all'";
    private static final String PRODUCT_AUTOCOMPLETE_PRODUCTS_CACHE = "product-autocomplete";

    private final ProductService productService;

    @Cacheable(cacheNames = PRODUCT_AUTOCOMPLETE_PRODUCTS_CACHE, key = ALL_PRODUCTS_KEY)
    public List<CachedProductInfo> findAll() {
        return productService.findAll()
                .stream()
                .map(ProductInfo::from)
                .map(product -> new CachedProductInfo(product, normalize(product.getName())))
                .sorted(autocompleteQualityOrder())
                .toList();
    }

    @CacheEvict(cacheNames = PRODUCT_AUTOCOMPLETE_PRODUCTS_CACHE, allEntries = true)
    public void evictAll() {
    }

    private String normalize(String keyword) {
        if (keyword == null) {
            return "";
        }
        return keyword.trim()
                .replaceAll("\\s+", " ")
                .toLowerCase(Locale.ROOT);
    }

    private Comparator<CachedProductInfo> autocompleteQualityOrder() {
        return Comparator
                .comparing(
                        (CachedProductInfo product) -> product.getProductInfo().getReviewCount(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
                .thenComparing(
                        product -> product.getProductInfo().getRating(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                )
                .thenComparing(product -> product.getProductInfo().getName());
    }
}
