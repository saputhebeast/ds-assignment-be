package com.microservices.enrollmentservice.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ComponentScan("com.microservices")
@EntityScan("com.microservices.enrollmentservice.core.model")
@EnableMongoRepositories("com.microservices.enrollmentservice.core.repository")
@SpringBootApplication
@EnableFeignClients(basePackages = "com.microservices.enrollmentservice.core.feign")
public class EnrollmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnrollmentServiceApplication.class, args);
	}

}
