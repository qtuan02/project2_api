package com.project4.repository;

import com.project4.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Modifying
    @Transactional
    void updateCategory(Long id, String name);
}
