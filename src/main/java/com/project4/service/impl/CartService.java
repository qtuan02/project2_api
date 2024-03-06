package com.project4.service.impl;

import com.project4.dto.CartDto;
import com.project4.entity.CartEntity;
import com.project4.entity.ProductEntity;
import com.project4.entity.UserEntity;
import com.project4.helper.CafeConstant;
import com.project4.helper.CafeMapDataJSON;
import com.project4.helper.CafeUtil;
import com.project4.repository.CartRepository;
import com.project4.repository.ProductRepository;
import com.project4.repository.UserRepository;
import com.project4.security.JwtFilter;
import com.project4.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService implements ICartService {
    private JwtFilter jwtFilter;
    private CartRepository cartRepository;
    private CafeMapDataJSON cafeMapDataJSON;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    @Autowired
    public CartService(JwtFilter jwtFilter, CartRepository cartRepository,
                       CafeMapDataJSON cafeMapDataJSON, ProductRepository productRepository,
                       UserRepository userRepository){
        this.jwtFilter = jwtFilter;
        this.cartRepository = cartRepository;
        this.cafeMapDataJSON = cafeMapDataJSON;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<CartDto>> getCarts() {
        try {
            if(jwtFilter.isAdmin()){
                return CafeUtil.getResponseEntityList((ArrayList)cartRepository.getCarts(), HttpStatus.OK);
            }else {
                return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> addCart(Map<String, String> requestMap) {
        try {
            UserEntity userObj = userRepository.findByEmail(jwtFilter.getCurrentUser());
            if(Objects.isNull(userObj)){
                return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }else{
                Optional<ProductEntity> product = productRepository.findById(Long.parseLong(requestMap.get("product_id")));
                if(product.isPresent() && product.get().getStatus().equals("true")){
                    cartRepository.save(cafeMapDataJSON.getCartFromMap(userObj, requestMap));
                    return CafeUtil.getResponseEntity(CafeConstant.ADD_SUCCESS, HttpStatus.OK);
                }else{
                    return CafeUtil.getResponseEntity(CafeConstant.PRODUCT_NOT_EXIST, HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<CartDto>> cartsByUser() {
        try {
            UserEntity userObj = userRepository.findByEmail(jwtFilter.getCurrentUser());
            if(Objects.isNull(userObj)){
                return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }else{
                return CafeUtil.getResponseEntityList((ArrayList)cartRepository.getCartsByUser(userObj.getId()), HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> deleteCart(Map<String, String> requestMap) {
        try {
            UserEntity userObj = userRepository.findByEmail(jwtFilter.getCurrentUser());
            if(!Objects.isNull(userObj)){
                if(jwtFilter.isAdmin()){
                    cartRepository.deleteById(Long.parseLong(requestMap.get("id")));
                    return CafeUtil.getResponseEntity(CafeConstant.DELETE_SUCCESS, HttpStatus.OK);
                }else if (jwtFilter.isUser()){
                    Optional<CartEntity> cart = cartRepository.findById(Long.parseLong(requestMap.get("id")));
                    if(cart.isPresent()){
                        if(cart.get().getUserCart().getId() == userObj.getId()){
                            cartRepository.deleteById(Long.parseLong(requestMap.get("id")));
                            return CafeUtil.getResponseEntity(CafeConstant.DELETE_SUCCESS, HttpStatus.OK);
                        }
                    }else{
                        return CafeUtil.getResponseEntity(CafeConstant.CART_NOT_EXIST, HttpStatus.BAD_REQUEST);
                    }
                }
            }
            return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> updateQuantityCart(Map<String, String> requestMap) {
        try {
            UserEntity userObj = userRepository.findByEmail(jwtFilter.getCurrentUser());
            if(!Objects.isNull(userObj)){
                Optional<CartEntity> cart = cartRepository.findById(Long.parseLong(requestMap.get("id")));
                if(cart.get().getUserCart().getId() == userObj.getId()){
                    if(cart.isPresent()){
                        cartRepository.save(cafeMapDataJSON.getCartUpdateFromMap(requestMap, cart.get()));
                        return CafeUtil.getResponseEntity(CafeConstant.UPDATE_SUCCESS, HttpStatus.OK);
                    }else {
                        return CafeUtil.getResponseEntity(CafeConstant.CART_NOT_EXIST, HttpStatus.BAD_REQUEST);
                    }
                }
            }
            return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }
}
