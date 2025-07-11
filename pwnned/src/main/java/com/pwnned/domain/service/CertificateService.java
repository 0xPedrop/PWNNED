package com.pwnned.domain.service;

import com.pwnned.domain.exception.CertificateNotFoundException;
import com.pwnned.domain.exception.LaboratoryNotFoundException;
import com.pwnned.domain.exception.UserNotFoundException;
import com.pwnned.domain.model.Certificate;
import com.pwnned.port.input.CertificateServicePort;
import com.pwnned.port.output.CertificateRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CertificateService implements CertificateServicePort {

    private final CertificateRepositoryPort certificateRepositoryPort;

    public CertificateService(CertificateRepositoryPort certificateRepositoryPort) {
        this.certificateRepositoryPort = certificateRepositoryPort;
    }

    @Override
    public Certificate createCertificate(Certificate certificate) {
        return certificateRepositoryPort.save(certificate);
    }

    @Override
    public List<Certificate> getAllCertificates() {
        return certificateRepositoryPort.findAll();
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
