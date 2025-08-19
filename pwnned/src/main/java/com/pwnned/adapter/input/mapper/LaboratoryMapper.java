package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LaboratoryMapper {

    LaboratoryMapper INSTANCE = Mappers.getMapper(LaboratoryMapper.class);

    Laboratory toModel(LaboratoryDTO laboratoryDTO);
    Laboratory toModel(LaboratoryEntity laboratoryEntity);

    LaboratoryDTO toDTO(Laboratory laboratory);
    LaboratoryEntity toEntity(Laboratory laboratory);

}
