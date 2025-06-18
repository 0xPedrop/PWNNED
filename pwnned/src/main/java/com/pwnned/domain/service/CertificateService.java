package com.pwnned.domain.service;

import com.pwnned.domain.exception.CertificateNotFoundException;
import com.pwnned.domain.exception.LaboratoryNotFoundException;
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
        List<Certificate> certificates = certificateRepositoryPort.findAll();
        if (certificates.isEmpty()) throw new CertificateNotFoundException("Certificates Not Found");
        return certificates;
    }

    @Override
    public Optional<Certificate> getSingleCertificate(UUID certificateId) {
        Optional<Certificate> certificate = certificateRepositoryPort.findById(certificateId);
        if (certificate.isEmpty()) throw new CertificateNotFoundException("Certificate " + certificateId + " Not Found");
        return certificate;
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
    public Optional<Certificate> getCertificateBySerialNumber(String serialNumber) {
        Optional<Certificate> certificate = certificateRepositoryPort.findBySerialNumber(serialNumber);
        if (certificate.isEmpty()) throw new CertificateNotFoundException("Certificate Not Found");
        return certificate;
    }
}
