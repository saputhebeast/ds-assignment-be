package com.microservices.courseservice.core.service.client;

import com.microservices.courseservice.core.payload.fiegn.ResponseEntityDto;
import com.microservices.courseservice.core.payload.fiegn.enums.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "user-service", url = "${user-service:http://localhost}:8000")
//@FeignClient(name = "user-service", url = "http://user-service:80")
@FeignClient(name = "user-service", url = "${feign.url.user}")
public interface UserServiceClient {

    @GetMapping("/api/v1/user")
    ResponseEntityDto getAllUserByOptionalRole(@RequestParam(value = "role", required = false) Role role);
}
