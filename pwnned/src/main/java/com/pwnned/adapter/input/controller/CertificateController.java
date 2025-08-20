package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.*;
import com.pwnned.adapter.input.mapper.CertificateMapper;
import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.input.mapper.PageableMapper;
import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.domain.model.Certificate;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.CertificateControllerPort;
import com.pwnned.port.input.CertificateServicePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1/certificates")
@RestController
public class CertificateController implements CertificateControllerPort {

    private final CertificateServicePort certificateServicePort;
    private final CertificateMapper certificateMapper;

    public CertificateController(CertificateServicePort certificateServicePort, CertificateMapper certificateMapper) {
        this.certificateServicePort = certificateServicePort;
        this.certificateMapper = certificateMapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<CertificateResponseDTO> createCertificate(@Valid @RequestBody CreateCertificateDTO certificateDTO) {
        Certificate createdCertificate = certificateServicePort.createCertificate(certificateDTO);
        CertificateResponseDTO createdCertificateDTO = certificateMapper.toDTO(createdCertificate);
        return ResponseEntity.status(201).body(createdCertificateDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<PageableDTO> getAllCertificates(@PageableDefault(size = 5, sort = "title") Pageable pageable) {
        Page<CertificateResponseDTO> certificateDTO = certificateServicePort.getAllCertificates(pageable);
        return ResponseEntity.ok(PageableMapper.INSTANCE.toDTO(certificateDTO));
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
        return ResponseEntity.ok(certificateMapper.toDTO(certificate));
    }
}
