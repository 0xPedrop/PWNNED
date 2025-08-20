package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.output.jpa.repository.entity.CertificateEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, UUID> {
    Optional<CertificateEntity> findBySerialNumber(String serialNumber);

    @Modifying
    @Transactional
    @Query("DELETE FROM CertificateEntity c WHERE c.user.userId = :userId")
    void deleteAllByUserId(@Param("userId") UUID userId);
}
