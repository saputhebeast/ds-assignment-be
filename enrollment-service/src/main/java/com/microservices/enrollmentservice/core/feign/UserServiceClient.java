package com.microservices.enrollmentservice.core.feign;

import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "user-service", url = "${user-service:http://localhost}:8000")
//@FeignClient(name = "user-service", url = "http://user-service:80")
@FeignClient(name = "user-service", url = "${feign.url.user}")
public interface UserServiceClient {

    @GetMapping("/api/v1/user/{id}")
    ResponseEntityDto getUserById(@PathVariable String id);
}
