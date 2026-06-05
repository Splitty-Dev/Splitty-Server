package com.chaegangjo.product.application;

import com.chaegangjo.product.dto.CachedProductInfo;
import com.chaegangjo.product.dto.ProductInfo;
import com.chaegangjo.product.service.ProductService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SearchProductUseCase {

    private static final int MIN_KEYWORD_LENGTH = 2;
    private static final int AUTOCOMPLETE_LIMIT = 7;

    private final ProductAutocompleteCache productAutocompleteCache;
    private final ProductService productService;

    public List<ProductInfo> execute(String keyword) {
        String normalizedKeyword = normalize(keyword);
        if (normalizedKeyword.length() < MIN_KEYWORD_LENGTH) {
            return List.of();
        }

        List<CachedProductInfo> products = productAutocompleteCache.findAll();

        List<ProductInfo> startsWithProducts = new ArrayList<>(AUTOCOMPLETE_LIMIT);
        List<ProductInfo> containsProducts = new ArrayList<>(AUTOCOMPLETE_LIMIT);

        for (CachedProductInfo product : products) {
            String normalizedName = product.getNormalizedName();
            if (normalizedName.startsWith(normalizedKeyword)) {
                startsWithProducts.add(product.getProductInfo());
                if (startsWithProducts.size() == AUTOCOMPLETE_LIMIT) {
                    return startsWithProducts;
                }
                continue;
            }

            if (containsProducts.size() < AUTOCOMPLETE_LIMIT && normalizedName.contains(normalizedKeyword)) {
                containsProducts.add(product.getProductInfo());
            }
        }

        startsWithProducts.addAll(containsProducts.subList(
                0,
                Math.min(AUTOCOMPLETE_LIMIT - startsWithProducts.size(), containsProducts.size())
        ));
        return startsWithProducts;
    }

    public List<ProductInfo> executeWithoutCache(String keyword) {
        List<ProductInfo> products = productService.findAll()
                .stream()
                .map(ProductInfo::from)
                .toList();

        return search(keyword, products);
    }

    private List<ProductInfo> search(String keyword, List<ProductInfo> products) {
        String normalizedKeyword = normalize(keyword);
        if (normalizedKeyword.length() < MIN_KEYWORD_LENGTH) {
            return List.of();
        }

        return products.stream()
                .filter(product -> containsNormalized(product.getName(), normalizedKeyword))
                .sorted(autocompleteOrder(normalizedKeyword))
                .limit(AUTOCOMPLETE_LIMIT)
                .toList();
    }

    private String normalize(String keyword) {
        if (keyword == null) {
            return "";
        }
        return keyword.trim()
                .replaceAll("\\s+", " ")
                .toLowerCase(Locale.ROOT);
    }

    private boolean containsNormalized(String productName, String normalizedKeyword) {
        return normalize(productName).contains(normalizedKeyword);
    }

    private Comparator<ProductInfo> autocompleteOrder(String normalizedKeyword) {
        return Comparator
                .comparing((ProductInfo product) -> !normalize(product.getName()).startsWith(normalizedKeyword))
                .thenComparing(ProductInfo::getReviewCount, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(ProductInfo::getRating, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(ProductInfo::getName);
    }
}
