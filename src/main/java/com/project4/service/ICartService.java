package com.project4.service;

import com.project4.dto.CartDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ICartService {
    ResponseEntity<List<CartDto>> getCarts();
    ResponseEntity<String> addCart(Map<String, String> requestMap);
    ResponseEntity<List<CartDto>> cartsByUser();
    ResponseEntity<String> deleteCart(Map<String, String> requestMap);
    ResponseEntity<String> updateQuantityCart(Map<String, String> requestMap);
}
