package com.pwnned.port.input;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.domain.model.Certificate;

public interface CertificateServicePort {
    Certificate createCertificate(CreateCertificateDTO createCertificateDTO);
    Page<CertificateResponseDTO> getAllCertificates(Pageable pageable);
    void deleteCertificate(Long certificateId);
    void deleteAllCertificates();
    Certificate getCertificateBySerialNumber(String serialNumber);
    boolean exists(Long userId, Long learningPathId);
}
