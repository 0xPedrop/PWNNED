package com.pwnned.port.output;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.LearningPath;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningPathRepositoryPort {
    LearningPath save(LearningPath learningPath);
    List<LearningPath> findAll();
    Optional<LearningPath> findById(UUID learningPathId);
    List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty);
    void deleteById(UUID learningPathId);
    void deleteAll();
}
