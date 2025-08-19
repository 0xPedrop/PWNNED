package com.pwnned.domain.service;

import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.exception.LaboratoryNotFoundException;
import com.pwnned.domain.exception.LearningPathNotFoundException;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.LearningPath;
import com.pwnned.port.input.LearningPathServicePort;
import com.pwnned.port.output.LearningPathRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LearningPathService implements LearningPathServicePort {

    private final LearningPathRepositoryPort learningPathRepositoryPort;

    public LearningPathService(LearningPathRepositoryPort learningPathRepositoryPort) {
        this.learningPathRepositoryPort = learningPathRepositoryPort;
    }

    @Override
    public LearningPath createLearningPath(LearningPath learningPath) {
        return learningPathRepositoryPort.save(learningPath);
    }

    @Override
    public List<LearningPath> getAllLearningPaths() {
        List<LearningPath> learningPaths = learningPathRepositoryPort.findAll();
        if (learningPaths.isEmpty()) throw new LearningPathNotFoundException("Learning Paths Not Found");
        return learningPaths;
    }

    @Override
    public Optional<LearningPath> getSingleLearningPath(UUID learningPathId) {
        Optional<LearningPath> learningPath = learningPathRepositoryPort.findById(learningPathId);
        if (learningPath.isEmpty()) throw new LaboratoryNotFoundException("Learning Path "
                + learningPathId + " Not Found");
        return learningPath;
    }

    @Override
    public void deleteLearningPath(UUID learningPathId) {
        Optional<LearningPath> learningPath = learningPathRepositoryPort.findById(learningPathId);
        if (learningPath.isEmpty()) throw new LearningPathNotFoundException("Learning Path "
                + learningPathId + " Not Found");
        learningPathRepositoryPort.deleteById(learningPathId);
    }

    @Override
    public void deleteAllLearningPaths() {
        learningPathRepositoryPort.deleteAll();
    }

    @Override
    public List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty) {
        List<LearningPath> learningPaths = learningPathRepositoryPort.getLearningPathsByDifficulty(difficulty);
        if (learningPaths.isEmpty()) throw new LearningPathNotFoundException("Learning Paths Not Found");
        return learningPaths;
    }
}
