package com.project4.controller.impl;

import com.project4.controller.ICartController;
import com.project4.dto.CartDto;
import com.project4.helper.CafeConstant;
import com.project4.helper.CafeUtil;
import com.project4.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CartController implements ICartController {
    @Autowired
    private ICartService cartService;

    @Override
    public ResponseEntity<List<CartDto>> getCarts() {
        try {
            return cartService.getCarts();
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<CartDto>> cartsByUser() {
        try {
            return cartService.cartsByUser();
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addCart(Map<String, String> requestMap) {
        try {
            return cartService.addCart(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCart(Map<String, String> requestMap) {
        try {
            return cartService.deleteCart(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateQuantityCart(Map<String, String> requestMap) {
        try {
            return cartService.updateQuantityCart(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
