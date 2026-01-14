package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.port.output.LaboratoryRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LaboratoryRepositoryAdapter implements LaboratoryRepositoryPort {

    private final LaboratoryRepository laboratoryRepository;
    private final LaboratoryMapper laboratoryMapper;

    public LaboratoryRepositoryAdapter(LaboratoryRepository laboratoryRepository, LaboratoryMapper laboratoryMapper) {
        this.laboratoryRepository = laboratoryRepository;
        this.laboratoryMapper = laboratoryMapper;
    }

    @Override
    public Laboratory save(Laboratory laboratory) {
        LaboratoryEntity entity = laboratoryMapper.toEntity(laboratory, new CycleAvoidingMappingContext());
        LaboratoryEntity saved = laboratoryRepository.save(entity);
        return laboratoryMapper.toModel(saved, new CycleAvoidingMappingContext());
    }

    @Override
    public Page<Laboratory> findAll(Pageable pageable) {
        return laboratoryRepository.findAll(pageable)
                .map(entity -> laboratoryMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public Optional<Laboratory> findById(UUID laboratoryId) {
        return laboratoryRepository.findById(laboratoryId)
                .map(entity -> laboratoryMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public List<Laboratory> getLaboratoriesByType(LaboratoryType laboratoryType) {
        return laboratoryRepository.findByLaboratoryType(laboratoryType).stream()
                .map(entity -> laboratoryMapper.toModel(entity, new CycleAvoidingMappingContext()))
                .toList();
    }

    @Override
    public void deleteById(UUID laboratoryId) {
        laboratoryRepository.deleteById(laboratoryId);
    }

    @Override
    public void deleteAll() {
        laboratoryRepository.deleteAll();
    }

    @Override
    public List<Laboratory> findByLearningPathId(UUID learningPathId) {
        return laboratoryRepository.findByLearningPath_LearningPathId(learningPathId).stream()
                .map(entity -> laboratoryMapper.toModel(entity, new CycleAvoidingMappingContext()))
                .toList();
    }

    @Override
    public void deleteAllByLearningPathId(UUID learningPathId) {
        laboratoryRepository.deleteAllByLearningPathId(learningPathId);
    }
}