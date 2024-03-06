package com.project4.service.impl;

import com.project4.dto.ProductDto;
import com.project4.entity.ProductEntity;
import com.project4.helper.CafeCheckDataJSON;
import com.project4.helper.CafeConstant;
import com.project4.helper.CafeMapDataJSON;
import com.project4.helper.CafeUtil;
import com.project4.repository.ProductRepository;
import com.project4.security.JwtFilter;
import com.project4.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ProductService implements IProductService {
    private ProductRepository productRepository;
    private JwtFilter jwtFilter;
    private CafeMapDataJSON cafeMapDataJSON;
    @Autowired
    public ProductService(ProductRepository productRepository, JwtFilter jwtFilter,
                          CafeMapDataJSON cafeMapDataJSON){
        this.productRepository = productRepository;
        this.jwtFilter = jwtFilter;
        this.cafeMapDataJSON = cafeMapDataJSON;
    }
    @Override
    public ResponseEntity<List<ProductDto>> getProducts() {
        try{
            return CafeUtil.getResponseEntityList((ArrayList)productRepository.getProducts(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProductsByCategory(Long id) {
        try{
            return CafeUtil.getResponseEntityList((ArrayList)productRepository.getProductsByCategory(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(CafeCheckDataJSON.checkDataProduct(requestMap)){
                    productRepository.save(cafeMapDataJSON.getProductEntityFromMap(requestMap));
                    return CafeUtil.getResponseEntity(CafeConstant.ADD_SUCCESS, HttpStatus.OK);
                }else{
                    return CafeUtil.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile fileImage) {
        try{
            if(jwtFilter.isAdmin()){
                if(Objects.isNull(cafeMapDataJSON.getAndCheckExtensionFile(fileImage.getOriginalFilename()))){
                    return CafeUtil.getResponseEntity("File không hợp lệ! Chỉ cho phép file jpg, png, jpeg.", HttpStatus.BAD_REQUEST);
                }
                if(!cafeMapDataJSON.isValidFileSize(fileImage.getSize())){
                    return CafeUtil.getResponseEntity("Dung lượng file phải nhỏ hơn 2mb!", HttpStatus.BAD_REQUEST);
                }
                return CafeUtil.getResponseEntity(cafeMapDataJSON.uploadAndGetImageName(fileImage), HttpStatus.OK);
            }else{
                return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        try {
            if (jwtFilter.isAdmin()){
                productRepository.deleteById(id);
                return CafeUtil.getResponseEntity(CafeConstant.DELETE_SUCCESS, HttpStatus.OK);
            }
            else{
                return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                Optional<ProductEntity> product = productRepository.findById(Long.parseLong(requestMap.get("id")));
                if(!product.isPresent()){
                    return CafeUtil.getResponseEntity(CafeConstant.PRODUCT_NOT_EXIST, HttpStatus.BAD_REQUEST);
                }else{
                    if(CafeCheckDataJSON.checkDataProduct(requestMap)){
                        productRepository.save(cafeMapDataJSON.getProductEntityUpdateFromMap(requestMap));
                        return CafeUtil.getResponseEntity(CafeConstant.UPDATE_SUCCESS, HttpStatus.OK);
                    }else{
                        return CafeUtil.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
                    }
                }
            }else{
                return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> changeStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                Optional<ProductEntity> product = productRepository.findById(Long.parseLong(requestMap.get("id")));
                if(product.isPresent()){
                    productRepository.changsStatus(Long.parseLong(requestMap.get("id")), requestMap.get("status"));
                    return CafeUtil.getResponseEntity(CafeConstant.UPDATE_SUCCESS, HttpStatus.OK);
                }else{
                    return CafeUtil.getResponseEntity(CafeConstant.PRODUCT_NOT_EXIST, HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }
}
