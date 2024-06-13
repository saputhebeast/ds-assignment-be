package com.microservices.contentservice.core.config;

import com.microservices.contentservice.core.payload.UserResponseDto;
import com.microservices.contentservice.core.util.ModuleUtils;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final ThreadLocal<String> jwtToken = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (!ModuleUtils.validString(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String userId = httpServletRequest.getHeader("userId");
        String userName = httpServletRequest.getHeader("userName");
        String authoritiesStr = httpServletRequest.getHeader("role");
        String name = httpServletRequest.getHeader("name");
        UserResponseDto user = new UserResponseDto(userId, name, userName, authoritiesStr);
        if (userId == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if (ModuleUtils.validString(authoritiesStr)) {
            simpleGrantedAuthorities = Arrays.stream(authoritiesStr.split(",")).distinct()
                    .filter(ModuleUtils::validString).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, user, simpleGrantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        jwtToken.set(authHeader.substring(7));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
