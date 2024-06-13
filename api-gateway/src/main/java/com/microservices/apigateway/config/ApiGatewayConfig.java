package com.microservices.apigateway.config;

import com.microservices.apigateway.filter.AuthenticationFilter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiGatewayConfig {

    @Value("${address.base.user-service}")
    private String userServiceAddress;

    @Value("${address.base.course-service}")
    private String courseServiceAddress;

    @Value("${address.base.content-service}")
    private String contentServiceAddress;

    @Value("${address.base.enrollment-service}")
    private String enrollmentServiceAddress;

    @Value("${address.base.notification-service}")
    private String notificationServiceAddress;

    @Value("${address.base.payment-service}")
    private String paymentServiceAddress;

    @NonNull
    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        String replacement = "/${segment}";
        return builder.routes()
                .route(p -> p
                        .path("/USER/**")
                        .filters(f -> f.rewritePath("/USER/(?<segment>.*)", replacement)
                                .filter(authenticationFilter))
                        .uri(userServiceAddress))
                .route(p -> p
                        .path("/COURSE/**")
                        .filters(f -> f.rewritePath("/COURSE/(?<segment>.*)", replacement)
                                .filter(authenticationFilter))
                        .uri(courseServiceAddress))
                .route(p -> p
                        .path("/CONTENT/**")
                        .filters(f -> f.rewritePath("/CONTENT/(?<segment>.*)", replacement)
                                .filter(authenticationFilter))
                        .uri(contentServiceAddress))
                .route(p -> p
                        .path("/ENROLLMENT/**")
                        .filters(f -> f.rewritePath("/ENROLLMENT/(?<segment>.*)", replacement)
                                .filter(authenticationFilter))
                        .uri(enrollmentServiceAddress))
                .route(p -> p
                        .path("/NOTIFICATION/**")
                        .filters(f -> f.rewritePath("/NOTIFICATION/(?<segment>.*)", replacement)
                                .filter(authenticationFilter))
                        .uri(notificationServiceAddress))
                .route(p -> p
                        .path("/PAYMENT/**")
                        .filters(f -> f.rewritePath("/PAYMENT/(?<segment>.*)", replacement)
                                .filter(authenticationFilter))
                        .uri(paymentServiceAddress))
                .build();
    }
}
