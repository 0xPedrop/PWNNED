package com.pwnned.port.output;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.domain.model.Certificate;

public interface CertificateRepositoryPort {
    Certificate save(Certificate certificate);
    Page<CertificateResponseDTO> findAll(Pageable pageable);
    Optional<Certificate> findById(Long certificateId);
    Optional<Certificate> findBySerialNumber(String serialNumber);
    void deleteById(Long certificateId);
    void deleteAll();
    void deleteAllByUserId(Long userId);
    boolean existsByUserIdAndLearningPathId(Long userId, Long learningPathId);
    void deleteByLearningPathId(Long learningPathId);
}
