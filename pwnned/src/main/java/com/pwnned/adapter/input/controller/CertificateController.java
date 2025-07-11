package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.input.mapper.CertificateMapper;
import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.domain.model.Certificate;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.CertificateControllerPort;
import com.pwnned.port.input.CertificateServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1/certificates")
@RestController
public class CertificateController implements CertificateControllerPort {

    private final CertificateServicePort certificateServicePort;

    public CertificateController(CertificateServicePort certificateServicePort) {
        this.certificateServicePort = certificateServicePort;
    }

    @Override
    @PostMapping
    public ResponseEntity<CertificateResponseDTO> createCertificate(CreateCertificateDTO certificateDTO) {
        Certificate certificate = CertificateMapper.INSTANCE.toModel(certificateDTO);
        Certificate createdCertificate = certificateServicePort.createCertificate(certificate);
        CertificateResponseDTO createdCertificateDTO = CertificateMapper.INSTANCE.toDTO(createdCertificate);
        return ResponseEntity.status(201).body(createdCertificateDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CertificateResponseDTO>> getAllCertificates() {
        List<CertificateResponseDTO> certificateResponseDTOS = certificateServicePort.getAllCertificates().stream()
                .map(CertificateMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(certificateResponseDTOS);
    }

    @Override
    @DeleteMapping("/{certificateId}")
    public ResponseEntity<String> deleteCertificate(UUID certificateId) {
        certificateServicePort.deleteCertificate(certificateId);
        return ResponseEntity.ok("Certificate " + certificateId + " deleted");
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteAllCertificate() {
        certificateServicePort.deleteAllCertificates();
        return ResponseEntity.ok("All Certificates Deleted");
    }

    @Override
    @GetMapping("/serial/{serialNumber}")
    public ResponseEntity<CertificateResponseDTO> getCertificateBySerialNumber(@PathVariable String serialNumber) {
        Certificate certificate = certificateServicePort.getCertificateBySerialNumber(serialNumber);
        return ResponseEntity.ok(CertificateMapper.INSTANCE.toDTO(certificate));
    }
}
