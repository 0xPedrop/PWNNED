package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.input.mapper.LearningPathMapper;
import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.adapter.output.jpa.repository.entity.LearningPathEntity;
import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.LearningPath;
import com.pwnned.port.output.LearningPathRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LearningPathRepositoryAdapter implements LearningPathRepositoryPort {

    private final LearningPathRepository learningPathRepository;
    private final LearningPathMapper learningPathMapper;

    public LearningPathRepositoryAdapter(LearningPathRepository learningPathRepository, LearningPathMapper learningPathMapper) {
        this.learningPathRepository = learningPathRepository;
        this.learningPathMapper = learningPathMapper;
    }

    @Override
    public LearningPath save(LearningPath learningPath) {
        LearningPathEntity learningPathEntity = learningPathMapper.toEntity(learningPath);
        LearningPathEntity savedLearningPath = learningPathRepository.save(learningPathEntity);
        return learningPathMapper.toModel(savedLearningPath);
    }

    @Override
    public Page<LearningPath> findAll(Pageable pageable) {
        return learningPathRepository.findAll(pageable).map(learningPathMapper::toModel);
    }

    @Override
    public Optional<LearningPath> findById(UUID learningPathId) {
        return learningPathRepository.findById(learningPathId).map(learningPathMapper::toModel);
    }

    @Override
    public List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty) {
        return learningPathRepository.findLearningPathsByDifficulty(difficulty)
                .stream().map(learningPathMapper::toModel).toList();
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
