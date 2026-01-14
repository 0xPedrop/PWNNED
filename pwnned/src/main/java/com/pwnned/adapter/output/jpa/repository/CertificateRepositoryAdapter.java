package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.mapper.CertificateMapper;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.CertificateEntity;
import com.pwnned.domain.model.Certificate;
import com.pwnned.port.output.CertificateRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CertificateRepositoryAdapter implements CertificateRepositoryPort {

    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    public CertificateRepositoryAdapter(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public Certificate save(Certificate certificate) {
        CertificateEntity entity = certificateMapper.toEntity(certificate, new CycleAvoidingMappingContext());
        CertificateEntity saved = certificateRepository.save(entity);
        return certificateMapper.toModel(saved, new CycleAvoidingMappingContext());
    }

    @Override
    public Page<CertificateResponseDTO> findAll(Pageable pageable) {
        return certificateRepository.findAll(pageable)
                .map(entity -> {
                    Certificate model = certificateMapper.toModel(entity, new CycleAvoidingMappingContext());
                    return certificateMapper.toDTO(model);
                });
    }

    @Override
    public Optional<Certificate> findById(UUID certificateId) {
        return certificateRepository.findById(certificateId)
                .map(entity -> certificateMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public Optional<Certificate> findBySerialNumber(String serialNumber) {
        return certificateRepository.findBySerialNumber(serialNumber)
                .map(entity -> certificateMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public void deleteById(UUID certificateId) {
        certificateRepository.deleteById(certificateId);
    }

    @Override
    public void deleteAll() {
        certificateRepository.deleteAll();
    }

    @Override
    public void deleteAllByUserId(UUID userId) {
        certificateRepository.deleteAllByUserId(userId);
    }
}