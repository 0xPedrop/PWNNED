package com.pwnned.port.output;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.LearningPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LearningPathRepositoryPort {
    LearningPath save(LearningPath learningPath);
    Page<LearningPath> findAll(Pageable pageable);
    Optional<LearningPath> findById(Long learningPathId);
    List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty);
    void deleteById(Long learningPathId);
    void deleteAll();
}
