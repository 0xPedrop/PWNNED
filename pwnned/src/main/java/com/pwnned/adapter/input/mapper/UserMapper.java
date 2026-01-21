package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import com.pwnned.domain.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LearningPathMapper.class, CertificateMapper.class})
public interface UserMapper {

    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "learningPathsAcessed", ignore = true)
    @Mapping(target = "experiencePoints", ignore = true)
    User toModel(UserDTO userDTO);

    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "learningPathsAcessed", ignore = true)
    User toModel(UserEntity userEntity, @Context CycleAvoidingMappingContext context);

    @Mapping(target = "experiencePoints", source = "experiencePoints")
    UserDTO toDTO(User user);

    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "learningPathsAcessed", ignore = true)
    UserEntity toEntity(User user, @Context CycleAvoidingMappingContext context);
}