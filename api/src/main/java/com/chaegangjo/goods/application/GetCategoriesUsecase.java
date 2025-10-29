package com.chaegangjo.goods.application;

import com.chaegangjo.goods.dto.CategoryInfo;
import com.chaegangjo.goods.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetCategoriesUsecase {

    private final CategoryService categoryService;

    public List<CategoryInfo> execute() {
        return categoryService.getCategories().stream()
                .map(CategoryInfo::from)
                .toList();
    }
}
