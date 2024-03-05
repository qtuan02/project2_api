package com.project4.controller.impl;

import com.project4.controller.IUserController;
import com.project4.dto.ProfileDto;
import com.project4.dto.UserDto;
import com.project4.helper.CafeConstant;
import com.project4.helper.CafeUtil;
import com.project4.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController implements IUserController {
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService){
        this.userService = userService;
    }

    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        try {
            return userService.register(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changeStatus(Map<String, String> requestMap) {
        try{
            return userService.updateStatus(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            return userService.changePassword(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changeRole(Map<String, String> requestMap) {
        try {
            return userService.changeRole(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        try{
            return userService.getUsers();
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProfileDto> getUser(Long id) {
        try {
            return userService.getUser(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityObject(new ProfileDto(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProfile(Map<String, String> requestMap) {
        try {
            return userService.updateProfile(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
