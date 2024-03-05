package com.project4.service.impl;

import com.project4.entity.CategoryEntity;
import com.project4.helper.CafeConstant;
import com.project4.helper.CafeMapDataJSON;
import com.project4.helper.CafeUtil;
import com.project4.repository.CategoryRepository;
import com.project4.security.JwtFilter;
import com.project4.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService implements ICategoryService {
    private CategoryRepository categoryRepository;
    private JwtFilter jwtFilter;
    private CafeMapDataJSON cafeMapDataJSON;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, JwtFilter jwtFilter,
                           CafeMapDataJSON cafeMapDataJSON){
        this.categoryRepository = categoryRepository;
        this.jwtFilter = jwtFilter;
        this.cafeMapDataJSON = cafeMapDataJSON;
    }
    @Override
    public ResponseEntity<List<CategoryEntity>> getCategories() {
        try {
            return CafeUtil.getResponseEntityList((ArrayList)categoryRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> addCategory(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                categoryRepository.save(cafeMapDataJSON.getCategoryFromMap(requestMap));
                return CafeUtil.getResponseEntity(CafeConstant.ADD_SUCCESS, HttpStatus.OK);
            }
            return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> deleteCategory(Long id) {
        try{
            if (jwtFilter.isAdmin()){
                Optional<CategoryEntity> categoryObj = categoryRepository.findById(id);
                if (!categoryObj.isPresent()){
                    return CafeUtil.getResponseEntity(CafeConstant.CATEGORY_NOT_EXIST, HttpStatus.BAD_REQUEST);
                }else{
                    categoryRepository.deleteById(id);
                    return CafeUtil.getResponseEntity(CafeConstant.DELETE_SUCCESS, HttpStatus.OK);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                Optional<CategoryEntity> categoryObj = categoryRepository.findById(Long.parseLong(requestMap.get("id")));
                if(!categoryObj.isPresent()){
                    return CafeUtil.getResponseEntity(CafeConstant.CATEGORY_NOT_EXIST, HttpStatus.BAD_REQUEST);
                }else{
                    categoryRepository.updateCategory(Long.parseLong(requestMap.get("id")), requestMap.get("name"));
                    return CafeUtil.getResponseEntity(CafeConstant.UPDATE_SUCCESS, HttpStatus.OK);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }
}
