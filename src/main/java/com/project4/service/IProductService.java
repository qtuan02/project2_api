package com.project4.service;

import com.project4.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IProductService {
    ResponseEntity<List<ProductDto>> getProducts();
    ResponseEntity<List<ProductDto>> getProductsByCategory(Long id);
    ResponseEntity<String> addProduct(Map<String, String> requestMap);
    ResponseEntity<String> uploadImage(MultipartFile fileImage);
    ResponseEntity<String> deleteProduct(Long id);
    ResponseEntity<String> updateProduct(Map<String, String> requestMap);
    ResponseEntity<String> changeStatus(Map<String, String> requestMap);
}
