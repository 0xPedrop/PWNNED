package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.adapter.output.jpa.repository.entity.CertificateEntity;
import com.pwnned.domain.model.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {UserMapper.class, LearningPathMapper.class})
public interface CertificateMapper {

    // Mapeamento DTO de Requisição -> Modelo de Domínio
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "learningPath", ignore = true)
    @Mapping(target = "certificateId", ignore = true)
    @Mapping(target = "issueDate", expression = "java(LocalDate.now())")
    @Mapping(target = "serialNumber", expression = "java(UUID.randomUUID().toString().replace(\"-\", \"\").toUpperCase())")
    Certificate toModel(CreateCertificateDTO certificateDTO);

    // Mapeamento Entidade -> Modelo de Domínio (usado apenas internamente)
    Certificate toModel(CertificateEntity certificateEntity);

    // Adicionado: Mapeamento Direto da Entidade para o DTO de Resposta
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "learningPathId", source = "learningPath.learningPathId")
    CertificateResponseDTO toDTO(CertificateEntity certificateEntity);

    // Mapeamento Modelo de Domínio -> DTO de Resposta (usado para o POST/criação)
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "learningPathId", source = "learningPath.learningPathId")
    CertificateResponseDTO toDTO(Certificate certificate);

    // Mapeamento Modelo de Domínio -> Entidade
    CertificateEntity toEntity(Certificate certificate);
}