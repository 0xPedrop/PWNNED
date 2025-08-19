package com.pwnned.port.output;

import com.pwnned.domain.model.Certificate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificateRepositoryPort {
    Certificate save(Certificate certificate);
    List<Certificate> findAll();
    Optional<Certificate> findById(UUID certificateId);
    Optional<Certificate> findBySerialNumber(String serialNumber);
    void deleteById(UUID certificateId);
    void deleteAll();
}
