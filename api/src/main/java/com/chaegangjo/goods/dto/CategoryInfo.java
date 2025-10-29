package com.chaegangjo.goods.dto;

import com.chaegangjo.goods.domain.Category;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryInfo (
        @Schema(example = "1")
        Long id,
        @Schema(example = "식품")
        String name) {

    public static CategoryInfo from(Category category) {
        return new CategoryInfo(
                category.getId(),
                category.getName()
        );
    }
}
