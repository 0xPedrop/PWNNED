package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.adapter.output.jpa.repository.entity.CertificateEntity;
import com.pwnned.domain.model.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {LocalDate.class, UUID.class})
public interface CertificateMapper {

    CertificateMapper INSTANCE = Mappers.getMapper(CertificateMapper.class);

    @Mapping(target = "certificateId", ignore = true) // O ID é gerado pelo banco de dados, não pelo DTO de criação
    @Mapping(target = "issueDate", expression = "java(LocalDate.now())") // Gera a data atual no backend
    @Mapping(target = "serialNumber", expression = "java(UUID.randomUUID().toString().replace(\"-\", \"\").toUpperCase())")
    Certificate toModel(CreateCertificateDTO certificateResponseDTO);
    Certificate toModel(CertificateEntity certificateEntity);

    CertificateResponseDTO toDTO(Certificate certificate);
    CertificateEntity toEntity(Certificate certificate);
}
