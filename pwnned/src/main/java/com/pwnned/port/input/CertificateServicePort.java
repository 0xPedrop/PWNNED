package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.domain.model.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificateServicePort {
    Certificate createCertificate(CreateCertificateDTO createCertificateDTO);
    Page<CertificateResponseDTO> getAllCertificates(Pageable pageable);
    void deleteCertificate(UUID certificateId);
    void deleteAllCertificates();
    Certificate getCertificateBySerialNumber(String serialNumber);
}
