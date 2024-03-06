package com.project4.repository;

import com.project4.dto.ProductDto;
import com.project4.entity.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductDto> getProducts();
    List<ProductDto> getProductsByCategory(Long id);
    @Modifying
    @Transactional
    void changsStatus(Long id, String status);
}
