package com.project4.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtSecurity jwtSecurity;

    @Autowired
    private CustomerUsersDetailsService customerUsersDetailsService;

    Claims claims = null;
    private String userName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().matches("/user/login|/user/register|/category/getCategories|/product/getProducts|/product/getProductsByCategory/*|/images/*")){
            filterChain.doFilter(request, response);
        }else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                token = authorizationHeader.substring(7);
                userName = jwtSecurity.extractUsername(token);
                claims = jwtSecurity.extractAllClaims(token);
            }

            if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = customerUsersDetailsService.loadUserByUsername(userName);
                if(jwtSecurity.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        }
    }

    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser(){
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){
        return userName;
    }


}
