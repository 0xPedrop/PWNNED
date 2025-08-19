package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.domain.enums.LaboratoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LaboratoryRepository extends JpaRepository<LaboratoryEntity, UUID> {
    List<LaboratoryEntity> findByLaboratoryType(LaboratoryType laboratoryType);
    List<LaboratoryEntity> findByLearningPathId(UUID learningPathId);
}
