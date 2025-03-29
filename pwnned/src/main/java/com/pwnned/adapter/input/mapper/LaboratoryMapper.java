package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.domain.model.Laboratory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LaboratoryMapper {

    LaboratoryMapper INSTANCE = Mappers.getMapper(LaboratoryMapper.class);

    Laboratory toModel(LaboratoryDTO labDTO);
    Laboratory toModel(LaboratoryEntity labEntity);

    LaboratoryDTO toDTO(Laboratory lab);
    LaboratoryEntity toEntity(Laboratory lab);
}
