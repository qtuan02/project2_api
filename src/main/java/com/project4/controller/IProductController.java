package com.project4.controller;

import com.project4.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface IProductController {
    @GetMapping("/getProducts")
    ResponseEntity<List<ProductDto>> getProducts();
    @GetMapping("/getProductsByCategory/{id}")
    ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long id);
    @PostMapping("/addProduct")
    ResponseEntity<String> addProduct(@RequestBody Map<String, String> requestMap);
    @PostMapping("/uploadImage")
    ResponseEntity<String> uploadImage(@RequestParam("fileImage")MultipartFile fileImage);
    @PostMapping("/delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Long id);
    @PostMapping("/updateProduct")
    ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);
    @PostMapping("/changeStatus")
    ResponseEntity<String> changeStatus(@RequestBody Map<String, String> requestMap);
}
