package com.microservices.enrollmentservice.core.feign;

import com.microservices.enrollmentservice.core.payload.MakePaymentDto;
import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${feign.url.payment}")
public interface PaymentServiceClient {
    @PostMapping("/api/v1/payment")
    ResponseEntity<Object> makePayment(@RequestBody MakePaymentDto makePaymentDto);
}
