package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.CertificateMapper;
import com.pwnned.adapter.output.jpa.repository.entity.CertificateEntity;
import com.pwnned.domain.model.Certificate;
import com.pwnned.port.output.CertificateRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CertificateRepositoryAdapter implements CertificateRepositoryPort {

    private final CertificateRepository certificateRepository;

    public CertificateRepositoryAdapter(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public Certificate save(Certificate certificate) {
        CertificateEntity certificateEntity = CertificateMapper.INSTANCE.toEntity(certificate);
        CertificateEntity savedCertificate = certificateRepository.save(certificateEntity);
        return CertificateMapper.INSTANCE.toModel(savedCertificate);
    }

    @Override
    public List<Certificate> findAll() {
        return certificateRepository.findAll().stream().map(CertificateMapper.INSTANCE::toModel).toList();
    }

    @Override
    public Optional<Certificate> findById(UUID certificateId) {
        return certificateRepository.findById(certificateId).map(CertificateMapper.INSTANCE::toModel);
    }

    @Override
    public Optional<Certificate> findBySerialNumber(String serialNumber) {
        return certificateRepository.findBySerialNumber(serialNumber).map(CertificateMapper.INSTANCE::toModel);
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
