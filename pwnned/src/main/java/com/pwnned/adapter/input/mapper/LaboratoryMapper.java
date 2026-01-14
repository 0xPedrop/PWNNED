package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.domain.model.Laboratory;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {LearningPathMapper.class})
public interface LaboratoryMapper {

    @Mapping(target = "learningPath", ignore = true)
    Laboratory toModel(LaboratoryDTO laboratoryDTO);

    @Mapping(target = "learningPath", source = "learningPath")
    Laboratory toModel(LaboratoryEntity laboratoryEntity, @Context CycleAvoidingMappingContext context);

    @Mapping(target = "learningPathId", source = "learningPath.learningPathId")
    LaboratoryDTO toDTO(Laboratory laboratory);

    @Mapping(target = "learningPath", source = "learningPath")
    LaboratoryEntity toEntity(Laboratory laboratory, @Context CycleAvoidingMappingContext context);
}