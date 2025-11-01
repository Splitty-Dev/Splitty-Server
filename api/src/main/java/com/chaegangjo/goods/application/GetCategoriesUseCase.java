package com.chaegangjo.goods.application;

import com.chaegangjo.goods.dto.CategoryInfo;
import com.chaegangjo.goods.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetCategoriesUseCase {

    private final CategoryService categoryService;

    public List<CategoryInfo> execute() {
        return categoryService.findAll().stream()
                .map(CategoryInfo::from)
                .toList();
    }
}
