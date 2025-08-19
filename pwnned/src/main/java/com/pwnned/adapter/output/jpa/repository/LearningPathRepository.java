package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.output.jpa.repository.entity.LearningPathEntity;
import com.pwnned.domain.enums.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LearningPathRepository extends JpaRepository<LearningPathEntity, UUID> {
    List<LearningPathEntity> findLearningPathsByDifficulty(Difficulty difficulty);
}
