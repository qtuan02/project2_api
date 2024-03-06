package com.project4.controller.impl;

import com.project4.controller.IProductController;
import com.project4.dto.ProductDto;
import com.project4.helper.CafeConstant;
import com.project4.helper.CafeUtil;
import com.project4.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController implements IProductController {
    @Autowired
    private IProductService productService;
    @Override
    public ResponseEntity<List<ProductDto>> getProducts() {
        try{
            return productService.getProducts();
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProductsByCategory(Long id) {
        try{
            return productService.getProductsByCategory(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try{
            return productService.addProduct(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile fileImage) {
        try {
            return productService.uploadImage(fileImage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        try {
            return productService.deleteProduct(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            return productService.updateProduct(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changeStatus(Map<String, String> requestMap) {
        try {
            return productService.changeStatus(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
