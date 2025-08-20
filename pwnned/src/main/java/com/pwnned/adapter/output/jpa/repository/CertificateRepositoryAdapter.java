package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.mapper.CertificateMapper;
import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.output.jpa.repository.entity.CertificateEntity;
import com.pwnned.domain.model.Certificate;
import com.pwnned.port.output.CertificateRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
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
        CertificateEntity certificateEntity = certificateMapper.toEntity(certificate);
        CertificateEntity savedCertificate = certificateRepository.save(certificateEntity);
        return certificateMapper.toModel(savedCertificate);
    }

    @Override
    public Page<CertificateResponseDTO> findAll(Pageable pageable) {
        return certificateRepository.findAll(pageable)
                .map(certificateMapper::toDTO);
    }

    @Override
    public Optional<Certificate> findById(UUID certificateId) {
        return certificateRepository.findById(certificateId).map(certificateMapper::toModel);
    }

    @Override
    public Optional<Certificate> findBySerialNumber(String serialNumber) {
        return certificateRepository.findBySerialNumber(serialNumber).map(certificateMapper::toModel);
    }

    @Override
    public void deleteById(UUID certificateId) {
        certificateRepository.deleteById(certificateId);
    }

    @Override
    public void deleteAll() {
        certificateRepository.deleteAll();
    }
}
