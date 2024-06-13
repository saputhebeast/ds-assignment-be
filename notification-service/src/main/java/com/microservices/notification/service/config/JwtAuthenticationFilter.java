package com.microservices.notification.service.config;

import com.microservices.notification.service.util.ModuleUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static ThreadLocal<String> jwtToken = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        if(!ModuleUtils.validString(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String userId = httpServletRequest.getHeader("userId");
        if (userId == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String authoritiesStr = httpServletRequest.getHeader("role");
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if(ModuleUtils.validString(authoritiesStr)) {
            simpleGrantedAuthorities=Arrays.stream(authoritiesStr.split(",")).distinct()
                    .filter(ModuleUtils::validString).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, simpleGrantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        jwtToken.set(authHeader.substring(7));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
