package com.microservices.contentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CourseContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseContentServiceApplication.class, args);
    }

}
