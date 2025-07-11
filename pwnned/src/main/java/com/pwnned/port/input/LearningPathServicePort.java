package com.pwnned.port.input;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.LearningPath;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningPathServicePort {
    LearningPath createLearningPath(LearningPath learningPath);
    List<LearningPath> getAllLearningPaths();
    LearningPath getSingleLearningPath(UUID learningPathId);
    void deleteLearningPath(UUID learningPathId);
    void deleteAllLearningPaths();
    List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty);
}
