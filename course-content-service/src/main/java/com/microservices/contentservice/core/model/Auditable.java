package com.microservices.contentservice.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class Auditable {
    protected Date createdAt;

    protected Date updatedAt;
}
