package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.PageableDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageableMapper {

    PageableDTO toDTO(Page page);
}
