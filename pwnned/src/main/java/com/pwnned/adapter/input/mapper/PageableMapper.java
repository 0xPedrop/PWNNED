package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.PageableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageableMapper {

    PageableMapper INSTANCE = Mappers.getMapper(PageableMapper.class);

    PageableDTO toDTO(Page page);
}
