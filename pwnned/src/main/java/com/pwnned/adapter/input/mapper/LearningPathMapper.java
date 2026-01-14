package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.LearningPathDTO;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.adapter.output.jpa.repository.entity.LearningPathEntity;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.LearningPath;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {LaboratoryMapper.class, CertificateMapper.class, UserMapper.class}) // Adicionado `uses` aqui
public interface LearningPathMapper {

    @Mapping(target = "laboratories", ignore = true)
    @Mapping(target = "usersAcessing", ignore = true)
    @Mapping(target = "certificate", ignore = true)
    LearningPath toModel(LearningPathDTO learningPathDTO);

    @Mapping(target = "laboratories", ignore = true)
    @Mapping(target = "usersAcessing", ignore = true)
    @Mapping(target = "certificate", ignore = true)
    LearningPath toModel(LearningPathEntity learningPathEntity, @Context CycleAvoidingMappingContext context);

    LearningPathDTO toDTO(LearningPath learningPath);

    @Mapping(target = "laboratories", ignore = true)
    @Mapping(target = "usersAcessing", ignore = true)
    @Mapping(target = "certificate", ignore = true)
    LearningPathEntity toEntity(LearningPath learningPath, @Context CycleAvoidingMappingContext context);
}