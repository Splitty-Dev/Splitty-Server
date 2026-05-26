package com.chaegangjo.product.repository;

import com.chaegangjo.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop7ByNameContainingIgnoreCase(String keyword);
}
