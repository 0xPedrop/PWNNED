package com.pwnned.port.input;

import com.pwnned.domain.model.Certificate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificateServicePort {
    Certificate createCertificate(Certificate certificate);
    List<Certificate> getAllCertificates();
    Optional<Certificate> getSingleCertificate(UUID certificateId);
    void deleteCertificate(UUID certificateId);
    void deleteAllCertificates();
    Optional<Certificate> getCertificateBySerialNumber(String serialNumber);
}
