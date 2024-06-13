package com.microservices.contentservice.core.mapper;

import com.microservices.contentservice.core.model.Content;
import com.microservices.contentservice.core.payload.ContentCreateDto;
import com.microservices.contentservice.core.payload.ContentResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    ContentResponseDto contentToContentResponseDto(Content content);

    List<ContentResponseDto> contentListToContentResponseDtoList(List<Content> content);

    Content contentCreateDtoToContent(ContentCreateDto contentCreateDto);
}
