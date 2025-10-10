package com.chaegangjo.goods.repository;


import com.chaegangjo.goods.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
