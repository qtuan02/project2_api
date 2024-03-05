package com.project4.service.impl;

import com.project4.dto.ProfileDto;
import com.project4.dto.UserDto;
import com.project4.entity.UserEntity;
import com.project4.helper.CafeCheckDataJSON;
import com.project4.helper.CafeConstant;
import com.project4.helper.CafeMapDataJSON;
import com.project4.helper.CafeUtil;
import com.project4.repository.RoleRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CafeMapDataJSON cafeMapDataJSON;
    private AuthenticationManager authenticationManager;
    private CustomerUsersDetailsService customerUsersDetailsService;
    private JwtSecurity jwtSecurity;
    private JwtFilter jwtFilter;

    @Autowired
    public UserService(UserRepository userRepository, CafeMapDataJSON cafeMapDataJSON,
                       AuthenticationManager authenticationManager, JwtSecurity jwtSecurity,
                       CustomerUsersDetailsService customerUsersDetailsService, JwtFilter jwtFilter,
                       RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.cafeMapDataJSON = cafeMapDataJSON;
        this.authenticationManager = authenticationManager;
        this.jwtSecurity = jwtSecurity;
        this.customerUsersDetailsService = customerUsersDetailsService;
        this.jwtFilter = jwtFilter;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        try{
            if(CafeCheckDataJSON.checkDataRegister(requestMap)){
                UserEntity userByEmail = userRepository.findByEmail(requestMap.get("email"));
                UserEntity userByPhone = userRepository.findByEmail(requestMap.get("phone"));
                if(Objects.isNull(userByEmail)){
                    if(Objects.isNull(userByPhone)){
                        userRepository.save(cafeMapDataJSON.getUserFromMap(requestMap));
                        return CafeUtil.getResponseEntity("Đăng kí thành công.", HttpStatus.OK);
                    }else{
                        return CafeUtil.getResponseEntity(CafeConstant.PHONE_EXIST, HttpStatus.BAD_REQUEST);
                    }
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
            UserEntity userChecUsername = userRepository.findByEmailOrPhone(requestMap.get("username"));
            if(Objects.isNull(userChecUsername)){
                return CafeUtil.getResponseEntity(CafeConstant.NOT_FIND_ACCOUNT, HttpStatus.BAD_REQUEST);
            }
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
                    return CafeUtil.getResponseEntity("Tài khoản đã bị vô hiệu hóa!!!", HttpStatus.BAD_REQUEST);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                UserEntity user = userRepository.findById(Long.parseLong(requestMap.get("id"))).get();
                if(Objects.isNull(user)){
                    return CafeUtil.getResponseEntity(CafeConstant.NOT_FIND_ACCOUNT, HttpStatus.BAD_REQUEST);
                }else{
                    userRepository.updateStatus(requestMap.get("status"), Long.parseLong(requestMap.get("id")));
                    return CafeUtil.getResponseEntity("Cập nhật trạng thái người dùng thành công.", HttpStatus.OK);
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
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            UserEntity userObj = userRepository.findByEmail(jwtFilter.getCurrentUser());
            if(Objects.isNull(userObj)){
                return CafeUtil.getResponseEntity(CafeConstant.NOT_FIND_ACCOUNT, HttpStatus.BAD_REQUEST);
            }else{
                if(requestMap.get("oldPassword").equals(userObj.getPassword())){
                    userRepository.changePassword(requestMap.get("newPassword"), userObj.getId());
                    return CafeUtil.getResponseEntity("Đổi mật khẩu thành công.", HttpStatus.OK);
                }else{
                    return CafeUtil.getResponseEntity("Mật khẩu không chính xác!!!", HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> changeRole(Map<String, String> requestMap) {
        try{
            if (jwtFilter.getCurrentUser().equals("huynhquoctuan200702@gmail.com")){
                userRepository.changeRole(Long.parseLong(requestMap.get("id")), roleRepository.findByName(requestMap.get("role")));
                return CafeUtil.getResponseEntity(CafeConstant.UPDATE_SUCCESS, HttpStatus.OK);
            }
            return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        try{
            if(jwtFilter.isAdmin()){
                return CafeUtil.getResponseEntityList(userRepository.getUsers(), HttpStatus.OK);
            }
            return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityList(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<ProfileDto> getUser(Long id) {
        try{
            UserEntity userObj = userRepository.findByEmail(jwtFilter.getCurrentUser());
            if(!Objects.isNull(userObj)){
                if(userObj.getId() == id){
                    return CafeUtil.getResponseEntityObject(userRepository.getUser(id), HttpStatus.OK);
                }else{
                    return CafeUtil.getResponseEntityObject(new ProfileDto(), HttpStatus.UNAUTHORIZED);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntityObject(new ProfileDto(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> updateProfile(Map<String, String> requestMap) {
        try{
            UserEntity userObj = userRepository.findByEmail(jwtFilter.getCurrentUser());
            if(Objects.isNull(userObj)){
                return CafeUtil.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }else{
                UserEntity user = cafeMapDataJSON.getProfileFromMap(requestMap);
                userRepository.updateProfileByUser(userObj.getId(), user.getFirstName(), user.getLastName(), user.getBirthday(), user.getAddress());
                return CafeUtil.getResponseEntity(CafeConstant.UPDATE_SUCCESS, HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtil.getResponseEntity(CafeConstant.ERROR, HttpStatus.BAD_REQUEST);
    }


}
