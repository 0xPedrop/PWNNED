package com.pwnned.port.output;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.domain.model.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificateRepositoryPort {
    Certificate save(Certificate certificate);
    Page<CertificateResponseDTO> findAll(Pageable pageable);
    Optional<Certificate> findById(UUID certificateId);
    Optional<Certificate> findBySerialNumber(String serialNumber);
    void deleteById(UUID certificateId);
    void deleteAll();
    void deleteAllByUserId(UUID userId);
}
