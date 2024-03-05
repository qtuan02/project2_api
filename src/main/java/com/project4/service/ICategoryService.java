package com.project4.service;

import com.project4.entity.CategoryEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    ResponseEntity<List<CategoryEntity>> getCategories();
    ResponseEntity<String> addCategory(Map<String, String> requestMap);
    ResponseEntity<String> deleteCategory(Long id);
    ResponseEntity<String> updateCategory(Map<String, String> requestMap);
}
