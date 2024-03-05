package com.project4.controller;

import com.project4.entity.CategoryEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/category")
public interface ICategoryController {
    @GetMapping("/getCategories")
    ResponseEntity<List<CategoryEntity>> getCategories();
    @PostMapping("/addCategory")
    ResponseEntity<String> addCategory(@RequestBody Map<String, String> requestMap);
    @PostMapping("/delete/{id}")
    ResponseEntity<String> deleteCategory(@PathVariable Long id);
    @PostMapping("/updateCategory")
    ResponseEntity<String> updateCategory(@RequestBody Map<String, String> requestMap);
}
