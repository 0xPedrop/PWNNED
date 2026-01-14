package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.adapter.input.dto.PageableDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificateControllerPort {
    ResponseEntity<CertificateResponseDTO> createCertificate(@RequestBody CreateCertificateDTO certificateDTO);
    ResponseEntity<PageableDTO> getAllCertificates(Pageable pageable);
    ResponseEntity<String> deleteCertificate(@PathVariable Long certificateId);
    ResponseEntity<String> deleteAllCertificate();
    ResponseEntity<CertificateResponseDTO> getCertificateBySerialNumber(@PathVariable String serialNumber);
}
