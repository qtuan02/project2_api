package com.project4.security;

import com.project4.entity.RoleEntity;
import com.project4.entity.UserEntity;
import com.project4.repository.RoleRepository;
import com.project4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private UserEntity userEntity;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userEntity = userRepository.findByEmailOrPhone(username);
        if(!Objects.isNull(userEntity)){
            return new User(username, userEntity.getPassword(), mapRolesToAuthorities(roleRepository.findAll()));
        }
        else{
            throw new UsernameNotFoundException("Username not found");
        }
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleEntity> roles){
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public UserEntity getUser(){
        return userEntity;
    }
}
