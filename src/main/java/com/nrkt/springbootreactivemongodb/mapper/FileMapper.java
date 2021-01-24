package com.nrkt.springbootreactivemongodb.mapper;


import com.nrkt.springbootreactivemongodb.decorator.FileDecorator;
import com.nrkt.springbootreactivemongodb.dto.response.FileResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper
@DecoratedWith(FileDecorator.class)
public interface FileMapper {

    @Mapping(target = "uploadDate", ignore = true)
    @Mapping(target = "id", source = "id")
    FileResponse fileResponse(String id);

    @Mapping(target = "uploadDate", source = "date")
    FileResponse fileResponse(String id, Date date);
}
