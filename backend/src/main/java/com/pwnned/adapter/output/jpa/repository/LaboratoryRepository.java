package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.domain.enums.LaboratoryType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboratoryRepository extends JpaRepository<LaboratoryEntity, Long> {
    List<LaboratoryEntity> findByLaboratoryType(LaboratoryType laboratoryType);
    List<LaboratoryEntity> findByLearningPath_LearningPathId(Long learningPathId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LaboratoryEntity l WHERE l.learningPath.learningPathId = :learningPathId")
    void deleteAllByLearningPathId(@Param("learningPathId") Long learningPathId);
}
