package com.chaegangjo.goods.service;

import com.chaegangjo.exception.GoodsException;
import com.chaegangjo.goods.domain.Category;
import com.chaegangjo.goods.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chaegangjo.exception.errorcode.GoodsErrorCode.CATEGORY_NOT_FOUND;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new GoodsException(CATEGORY_NOT_FOUND));
    }
}
