package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.CertificateEntity;
import com.pwnned.domain.model.Certificate;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, LearningPathMapper.class})
public interface CertificateMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "learningPath", ignore = true)
    @Mapping(target = "certificateId", ignore = true)
    Certificate toModel(CreateCertificateDTO certificateDTO);

    Certificate toModel(CertificateEntity certificateEntity, @Context CycleAvoidingMappingContext context);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "learningPathId", source = "learningPath.learningPathId")
    CertificateResponseDTO toDTO(Certificate certificate);

    CertificateEntity toEntity(Certificate certificate, @Context CycleAvoidingMappingContext context);
}