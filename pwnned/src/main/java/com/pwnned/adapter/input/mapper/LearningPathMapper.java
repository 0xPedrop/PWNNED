package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.LearningPathDTO;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.adapter.output.jpa.repository.entity.LearningPathEntity;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.LearningPath;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LearningPathMapper {

    LearningPathMapper INSTANCE = Mappers.getMapper(LearningPathMapper.class);

    LearningPath toModel(LearningPathDTO learningPathDTO);
    LearningPath toModel(LearningPathEntity learningPathEntity);

    LearningPathDTO toDTO(LearningPath learningPath);
    LearningPathEntity toEntity(LearningPath learningPath);
}
