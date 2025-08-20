package com.pwnned.domain.service;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.domain.exception.CertificateNotFoundException;
import com.pwnned.domain.exception.LaboratoryNotFoundException;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CertificateService implements CertificateServicePort {

    private final CertificateRepositoryPort certificateRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final LearningPathRepositoryPort learningPathRepositoryPort;

    public CertificateService(CertificateRepositoryPort certificateRepositoryPort, UserRepositoryPort userRepositoryPort, LearningPathRepositoryPort learningPathRepositoryPort) {
        this.certificateRepositoryPort = certificateRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.learningPathRepositoryPort = learningPathRepositoryPort;
    }

    @Override
    public Certificate createCertificate(CreateCertificateDTO certificateDTO) {
        User user = userRepositoryPort.findById(certificateDTO.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + certificateDTO.userId()));

        LearningPath learningPath = learningPathRepositoryPort.findById(certificateDTO.learningPathId())
                .orElseThrow(() -> new RuntimeException("Learning Path not found with ID: " +
                        certificateDTO.learningPathId()));

        Certificate certificate = new Certificate(certificateDTO.title());
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
    public void deleteCertificate(UUID certificateId) {
        Optional<Certificate> certificate = certificateRepositoryPort.findById(certificateId);
        if (certificate.isEmpty()) throw new CertificateNotFoundException("Certificate " + certificateId + " Not Found");
        certificateRepositoryPort.deleteById(certificateId);
    }

    @Override
    public void deleteAllCertificates() {
        certificateRepositoryPort.deleteAll();
    }

    @Override
    public Certificate getCertificateBySerialNumber(String serialNumber) {
        return certificateRepositoryPort.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new UserNotFoundException("Certificate not found with Serial Number: "
                        + serialNumber));
    }
}
