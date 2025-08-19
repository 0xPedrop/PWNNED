package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.CertificateResponseDTO;
import com.pwnned.adapter.input.dto.CreateCertificateDTO;
import com.pwnned.adapter.input.mapper.CertificateMapper;
import com.pwnned.domain.model.Certificate;
import com.pwnned.port.input.CertificateControllerPort;
import com.pwnned.port.input.CertificateServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/certificates")
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
        return ResponseEntity.ok(createdCertificateDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CertificateResponseDTO>> getAllCertificates() {
        List<CertificateResponseDTO> certificateDTOS = certificateServicePort.getAllCertificates()
                .stream()
                .map(CertificateMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(certificateDTOS);
    }

    @Override
    @GetMapping("/{certificateId}")
    public ResponseEntity<CertificateResponseDTO> getSingleCertificate(UUID certificateId) {
        return certificateServicePort.getSingleCertificate(certificateId)
                .map(certificate -> ResponseEntity.ok(CertificateMapper.INSTANCE.toDTO(certificate)))
                .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<Optional<CertificateResponseDTO>> getCertificateBySerialNumber(@PathVariable String serialNumber) {
        Optional<Certificate> certificate = certificateServicePort.getCertificateBySerialNumber(serialNumber);
        return certificate.map(CertificateMapper.INSTANCE::toDTO)
                .map(dto -> ResponseEntity.ok(Optional.of(dto)))
                .orElse(ResponseEntity.notFound().build());
    }
}
