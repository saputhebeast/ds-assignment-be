package com.microservices.contentservice.core.model;

import com.microservices.contentservice.core.type.ApprovalStatus;
import com.microservices.contentservice.core.type.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Document(collection = "content")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Content extends Auditable{

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Indexed
    private String courseId;

    @Field(targetType = FieldType.STRING)
    private String contentTitle;

    @Field(targetType = FieldType.STRING)
    private ContentType contentType;

    @Field(targetType = FieldType.DATE_TIME)
    private Date publishedDate;

    @Field(targetType = FieldType.BOOLEAN)
    private Boolean visible;

    @Field(targetType = FieldType.STRING)
    private String contentAccessLink;

    @Field(targetType = FieldType.BOOLEAN)
    private Boolean active;

    @Field(targetType = FieldType.STRING)
    private ApprovalStatus approved;
}
