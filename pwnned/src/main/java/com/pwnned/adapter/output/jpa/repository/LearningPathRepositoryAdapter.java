package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.LearningPathMapper;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
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
        LearningPathEntity entity = learningPathMapper.toEntity(learningPath, new CycleAvoidingMappingContext());
        LearningPathEntity saved = learningPathRepository.save(entity);
        return learningPathMapper.toModel(saved, new CycleAvoidingMappingContext());
    }

    @Override
    public Page<LearningPath> findAll(Pageable pageable) {
        return learningPathRepository.findAll(pageable)
                .map(entity -> learningPathMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public Optional<LearningPath> findById(UUID learningPathId) {
        return learningPathRepository.findById(learningPathId)
                .map(entity -> learningPathMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public List<LearningPath> getLearningPathsByDifficulty(Difficulty difficulty) {
        return learningPathRepository.findLearningPathsByDifficulty(difficulty).stream()
                .map(entity -> learningPathMapper.toModel(entity, new CycleAvoidingMappingContext()))
                .toList();
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