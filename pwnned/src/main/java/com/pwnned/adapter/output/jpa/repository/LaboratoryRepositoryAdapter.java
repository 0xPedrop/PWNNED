package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.User;
import com.pwnned.port.output.LaboratoryRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LaboratoryRepositoryAdapter implements LaboratoryRepositoryPort {

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryRepositoryAdapter(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    @Override
    public Laboratory save(Laboratory laboratory) {
        LaboratoryEntity laboratoryEntity = LaboratoryMapper.INSTANCE.toEntity(laboratory);
        LaboratoryEntity savedLaboratory = laboratoryRepository.save(laboratoryEntity);
        return LaboratoryMapper.INSTANCE.toModel(savedLaboratory);
    }

    @Override
    public List<Laboratory> findAll() {
        return laboratoryRepository.findAll().stream().map(LaboratoryMapper.INSTANCE::toModel).toList();
    }

    @Override
    public Optional<Laboratory> findById(UUID laboratoryId) {
        return laboratoryRepository.findById(laboratoryId).map(LaboratoryMapper.INSTANCE::toModel);
    }

    @Override
    public List<Laboratory> getLaboratoriesByType(LaboratoryType laboratoryType) {
        return laboratoryRepository.findByLaboratoryType(laboratoryType).stream().map(LaboratoryMapper.INSTANCE::toModel).toList();
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
        return laboratoryRepository.findByLearningPathId(learningPathId).stream()
                .map(LaboratoryMapper.INSTANCE::toModel)
                .toList();
    }
}
