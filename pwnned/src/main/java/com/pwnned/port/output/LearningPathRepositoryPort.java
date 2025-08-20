package com.pwnned.port.output;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.LearningPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningPathRepositoryPort {
    LearningPath save(LearningPath learningPath);
    Page<LearningPath> findAll(Pageable pageable);
    Optional<LearningPath> findById(UUID learningPathId);
    List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty);
    void deleteById(UUID learningPathId);
    void deleteAll();
}
