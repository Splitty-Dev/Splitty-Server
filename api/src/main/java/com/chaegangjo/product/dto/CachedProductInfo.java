package com.chaegangjo.product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CachedProductInfo {

    private final ProductInfo productInfo;
    private final String normalizedName;

    @JsonCreator
    public CachedProductInfo(
            @JsonProperty("productInfo") ProductInfo productInfo,
            @JsonProperty("normalizedName") String normalizedName
    ) {
        this.productInfo = productInfo;
        this.normalizedName = normalizedName;
    }
}
