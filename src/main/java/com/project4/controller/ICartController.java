package com.project4.controller;

import com.project4.dto.CartDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/cart")
public interface ICartController {
    @GetMapping("/getCarts")
    ResponseEntity<List<CartDto>> getCarts();
    @GetMapping("/getCartByUser")
    ResponseEntity<List<CartDto>> cartsByUser();
    @PostMapping("/addCart")
    ResponseEntity<String> addCart(@RequestBody Map<String, String>requestMap);
    @PostMapping("/deleteCart")
    ResponseEntity<String> deleteCart(@RequestBody Map<String, String> requestMap);
    @PostMapping("/updateQuantityCart")
    ResponseEntity<String> updateQuantityCart(@RequestBody Map<String, String> requestMap);
}
