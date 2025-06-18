package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.output.jpa.repository.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, UUID> {
    Optional<CertificateEntity> findBySerialNumber(String serialNumber);
}
