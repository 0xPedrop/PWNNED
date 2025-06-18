package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.input.mapper.LearningPathMapper;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.adapter.output.jpa.repository.entity.LearningPathEntity;
import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.LearningPath;
import com.pwnned.port.output.LearningPathRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LearningPathRepositoryAdapter implements LearningPathRepositoryPort {

    private final LearningPathRepository learningPathRepository;

    public LearningPathRepositoryAdapter(LearningPathRepository learningPathRepository) {
        this.learningPathRepository = learningPathRepository;
    }

    @Override
    public LearningPath save(LearningPath learningPath) {
        LearningPathEntity learningPathEntity = LearningPathMapper.INSTANCE.toEntity(learningPath);
        LearningPathEntity savedLearningPath = learningPathRepository.save(learningPathEntity);
        return LearningPathMapper.INSTANCE.toModel(savedLearningPath);
    }

    @Override
    public List<LearningPath> findAll() {
        return learningPathRepository.findAll().stream().map(LearningPathMapper.INSTANCE::toModel).toList();
    }

    @Override
    public Optional<LearningPath> findById(UUID learningPathId) {
        return learningPathRepository.findById(learningPathId).map(LearningPathMapper.INSTANCE::toModel);
    }

    @Override
    public List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty) {
        return learningPathRepository.findLearningPathsByDifficulty(difficulty)
                .stream().map(LearningPathMapper.INSTANCE::toModel).toList();
    }

    @Override
    public void deleteById(UUID learningPathId) {
        learningPathRepository.deleteById(learningPathId);
    }

    @Override
    public void deleteAll() {
        learningPathRepository.deleteAll();
    }
}
