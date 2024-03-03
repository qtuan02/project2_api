package com.project4.helper;

import com.project4.entity.UserEntity;
import com.project4.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
public class CafeMapDataJSON {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public CafeMapDataJSON(){}

    public UserEntity getUserFromMap(Map<String, String> requestMap){
        UserEntity user = new UserEntity();
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setPhone(requestMap.get("phone"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        user.setStatus("true");
        user.setRole(roleRepository.findByName("user"));
        return user;
    }
}
