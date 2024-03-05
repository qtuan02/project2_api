package com.project4.helper;

import com.project4.dto.UserDto;
import com.project4.entity.CategoryEntity;
import com.project4.entity.UserEntity;
import com.project4.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;

@Component
public class CafeMapDataJSON {
    @Autowired
    private RoleRepository roleRepository;

    public CafeMapDataJSON(){}

    public UserEntity getUserFromMap(Map<String, String> requestMap){
        UserEntity user = new UserEntity();
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setPhone(requestMap.get("phone"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("true");
        user.setRole(roleRepository.findByName("user"));
        return user;
    }

    public UserEntity getProfileFromMap(Map<String, String> requestMap){
        UserEntity user = new UserEntity();
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setAddress(requestMap.get("address"));
        user.setBirthday(LocalDate.parse(requestMap.get("birthday")));
        return user;
    }

    public CategoryEntity getCategoryFromMap(Map<String, String> requestMap){
        CategoryEntity category = new CategoryEntity();
        category.setName(requestMap.get("name"));
        return category;
    }
}
