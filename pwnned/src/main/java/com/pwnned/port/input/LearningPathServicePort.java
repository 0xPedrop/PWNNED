package com.pwnned.port.input;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.LearningPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningPathServicePort {
    LearningPath createLearningPath(LearningPath learningPath);
    Page<LearningPath> getAllLearningPaths(Pageable pageable);
    LearningPath getSingleLearningPath(UUID learningPathId);
    void deleteLearningPath(UUID learningPathId);
    void deleteAllLearningPaths(Pageable pageable);
    List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty);
}
