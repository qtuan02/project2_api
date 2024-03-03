package com.project4.service.impl;

import com.project4.entity.UserEntity;
import com.project4.helper.CafeCheckDataJSON;
import com.project4.helper.CafeConstant;
import com.project4.helper.CafeMapDataJSON;
import com.project4.helper.CafeUtil;
import com.project4.repository.UserRepository;
import com.project4.security.CustomerUsersDetailsService;
import com.project4.security.JwtFilter;
import com.project4.security.JwtSecurity;
import com.project4.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;
    private CafeMapDataJSON cafeMapDataJSON;
    private AuthenticationManager authenticationManager;
    private CustomerUsersDetailsService customerUsersDetailsService;
    private JwtSecurity jwtSecurity;
    private JwtFilter jwtFilter;

    @Autowired
    public UserService(UserRepository userRepository, CafeMapDataJSON cafeMapDataJSON,
                       AuthenticationManager authenticationManager, JwtSecurity jwtSecurity,
                       CustomerUsersDetailsService customerUsersDetailsService, JwtFilter jwtFilter){
        this.userRepository = userRepository;
        this.cafeMapDataJSON = cafeMapDataJSON;
        this.authenticationManager = authenticationManager;
        this.jwtSecurity = jwtSecurity;
        this.customerUsersDetailsService = customerUsersDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        try{
            if(CafeCheckDataJSON.checkDataRegister(requestMap)){
                UserEntity user = userRepository.findByEmail(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userRepository.save(cafeMapDataJSON.getUserFromMap(requestMap));
                    return CafeUtil.getResponseEntity("Đăng kí thành công.", HttpStatus.OK);
                }else{
                    return CafeUtil.getResponseEntity(CafeConstant.EMAIL_EXIST, HttpStatus.BAD_REQUEST);
                }
            }else{
                return CafeUtil.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("username"), requestMap.get("password")));
            if (auth.isAuthenticated()){
                UserEntity user = customerUsersDetailsService.getUser();
                if(customerUsersDetailsService.getUser().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtSecurity.generateToken(customerUsersDetailsService.getUser().getEmail(),
                                    customerUsersDetailsService.getUser().getRole().getName()) + "\"}",
                            HttpStatus.OK);
                }else{
                    return CafeUtil.getResponseEntity("Tài khoản đang bị vô hiệu hóa!!!", HttpStatus.BAD_REQUEST);
                }
            }else{
                return CafeUtil.getResponseEntity("Tài khoản không tồn tại!!!", HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }
}
