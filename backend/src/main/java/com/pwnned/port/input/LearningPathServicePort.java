package com.pwnned.port.input;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.LearningPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface LearningPathServicePort {
    LearningPath createLearningPath(LearningPath learningPath);
    Page<LearningPath> getAllLearningPaths(Pageable pageable);
    LearningPath getSingleLearningPath(Long learningPathId);
    void deleteLearningPath(Long learningPathId);
    void deleteAllLearningPaths(Pageable pageable);
    List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty);
}
