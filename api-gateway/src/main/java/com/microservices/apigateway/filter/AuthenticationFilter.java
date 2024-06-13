package com.microservices.apigateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.microservices.apigateway.exception.UnAuthorizedException;
import com.microservices.apigateway.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter implements GatewayFilter {
    @NonNull
    private final UserService userService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final List<String> publicUrls = List.of("/api/v1/auth/**", "/actuator/**");

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();
        if (!isPublicUrl(requestUrl)) {
            return chain.filter(validateRequestWithToken(exchange));
        }
        return chain.filter(exchange);
    }

    private boolean isPublicUrl(String url) {
        for (String publicUrl : publicUrls) {
            if (pathMatcher.match(publicUrl, url)) {
                return true;
            }
        }
        return false;
    }

    private ServerWebExchange validateRequestWithToken(ServerWebExchange exchange) throws UnAuthorizedException {
        try {
            log.info("validateRequestWithToken: Execution Started");
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new UnAuthorizedException("Missing authorization header");
            }

            String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                JsonNode currentUser = userService.getCurrentUser(authHeader);
                String username = currentUser.get("email").asText();
                String userId = currentUser.get("id").asText();
                String role = currentUser.get("role").asText();

                exchange.getRequest().mutate().header("userId", userId);
                exchange.getRequest().mutate().header("username", username);
                exchange.getRequest().mutate().header("role", role);

                log.info("validateRequestWithToken: Execution Completed");
                return exchange;
            } else {
                throw new UnAuthorizedException("No Auth header found");
            }
        } catch (Exception e) {
            log.error("validateRequestWithToken: error occrrured: {}", e.getMessage());
            throw new UnAuthorizedException(e.getMessage());
        }
    }
}