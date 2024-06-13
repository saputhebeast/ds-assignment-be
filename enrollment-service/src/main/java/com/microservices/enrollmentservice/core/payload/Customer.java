package com.microservices.enrollmentservice.core.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Customer {
    @NotNull
    private String name;
    private String address;
}
