package com.microservices.contentservice.core.config;

import com.microservices.contentservice.core.model.Content;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MongoListener extends AbstractMongoEventListener<Content> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Content> event) {
        super.onBeforeConvert(event);

        Date dateNow = new Date();

        event.getSource().setCreatedAt(dateNow);
        event.getSource().setUpdatedAt(dateNow);
    }
}
