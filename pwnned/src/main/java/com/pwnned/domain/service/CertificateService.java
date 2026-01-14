package com.pwnned.domain.service;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.adapter.output.jpa.repository.util.SnowflakeIdGenerator;
import com.pwnned.adapter.output.redis.CertificateRedisAdapter;
import com.pwnned.domain.exception.CertificateNotFoundException;
import com.pwnned.domain.exception.UserNotFoundException;
import com.pwnned.domain.model.Certificate;
import com.pwnned.domain.model.LearningPath;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.CertificateServicePort;
import com.pwnned.port.output.CertificateRepositoryPort;
import com.pwnned.port.output.LearningPathRepositoryPort;
import com.pwnned.port.output.UserRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CertificateService implements CertificateServicePort {

    private final CertificateRepositoryPort certificateRepositoryPort;
    private final CertificateRedisAdapter certificateRedisAdapter;
    private final UserRepositoryPort userRepositoryPort;
    private final LearningPathRepositoryPort learningPathRepositoryPort;
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    public CertificateService(CertificateRepositoryPort certificateRepositoryPort, CertificateRedisAdapter certificateRedisAdapter, UserRepositoryPort userRepositoryPort, LearningPathRepositoryPort learningPathRepositoryPort, SnowflakeIdGenerator snowflakeIdGenerator) {
        this.certificateRepositoryPort = certificateRepositoryPort;
        this.certificateRedisAdapter = certificateRedisAdapter;
        this.userRepositoryPort = userRepositoryPort;
        this.learningPathRepositoryPort = learningPathRepositoryPort;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
    }

    @Override
    public Certificate createCertificate(CreateCertificateDTO certificateDTO) {
        User user = userRepositoryPort.findById(certificateDTO.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + certificateDTO.userId()));

        LearningPath learningPath = learningPathRepositoryPort.findById(certificateDTO.learningPathId())
                .orElseThrow(() -> new RuntimeException("Learning Path not found with ID: " +
                        certificateDTO.learningPathId()));

        Certificate certificate = new Certificate(certificateDTO.title());
        certificate.setCertificateId(snowflakeIdGenerator.nextId());

        certificate.setUrl(certificateDTO.url());
        certificate.setUser(user);
        certificate.setLearningPath(learningPath);

        return certificateRepositoryPort.save(certificate);
    }

    @Override
    public Page<CertificateResponseDTO> getAllCertificates(Pageable pageable) {
        return certificateRepositoryPort.findAll(pageable);
    }

    @Override
    public void deleteCertificate(Long certificateId) {
        Certificate certificate = certificateRepositoryPort.findById(certificateId)
                .orElseThrow(() -> new CertificateNotFoundException("Certificate " + certificateId + " Not Found"));

        certificateRepositoryPort.deleteById(certificateId);

        certificateRedisAdapter.invalidateCacheForCertificateBySerialNumber(certificate.getSerialNumber());
    }

    @Override
    public void deleteAllCertificates() {
        certificateRepositoryPort.deleteAll();
        certificateRedisAdapter.deleteAllCachedCertificates();
    }

    @Override
    public Certificate getCertificateBySerialNumber(String serialNumber) {
        Optional<Certificate> cachedCertificate = certificateRedisAdapter.getCertificateBySerialNumber(serialNumber);

        if (cachedCertificate.isPresent()) {
            return cachedCertificate.get();
        }

        Certificate certificate = certificateRepositoryPort.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new UserNotFoundException("Certificate not found with Serial Number: "
                        + serialNumber));

        certificateRedisAdapter.cacheCertificateBySerialNumber(serialNumber, certificate);
        return certificate;
    }
}
