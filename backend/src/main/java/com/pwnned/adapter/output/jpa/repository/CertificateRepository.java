package com.pwnned.adapter.output.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pwnned.adapter.output.jpa.repository.entity.CertificateEntity;

import jakarta.transaction.Transactional;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, Long> {
    Optional<CertificateEntity> findBySerialNumber(String serialNumber);

    @Modifying
    @Transactional
    @Query("DELETE FROM CertificateEntity c WHERE c.user.userId = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    boolean existsByUser_UserIdAndLearningPath_LearningPathId(Long userId, Long learningPathId);
    void deleteByLearningPath_LearningPathId(Long learningPathId);
}
