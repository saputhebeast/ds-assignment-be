package com.microservices.courseservice.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ComponentScan("com.microservices")
@EntityScan("com.microservices.courseservice.core.model")
@EnableMongoRepositories("com.microservices.courseservice.core.repository")
@SpringBootApplication
@EnableFeignClients(basePackages = "com.microservices.courseservice.core.service.client")
@ImportAutoConfiguration(FeignAutoConfiguration.class)
public class CourseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseServiceApplication.class, args);
	}

}
