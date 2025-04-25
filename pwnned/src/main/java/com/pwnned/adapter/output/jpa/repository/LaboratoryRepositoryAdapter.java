package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.output.jpa.repository.entity.LaboratoryEntity;
import com.pwnned.domain.enums.LabType;
import com.pwnned.domain.model.Laboratory;
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
    public Laboratory save(Laboratory lab) {
        LaboratoryEntity labEntity = LaboratoryMapper.INSTANCE.toEntity(lab);
        LaboratoryEntity savedLab= laboratoryRepository.save(labEntity);
        return LaboratoryMapper.INSTANCE.toModel(savedLab);
    }

    @Override
    public List<Laboratory> findAll() {
        return laboratoryRepository.findAll().stream().map(LaboratoryMapper.INSTANCE::toModel).toList();
    }

    @Override
    public Optional<Laboratory> findById(UUID labId) {
        return laboratoryRepository.findById(labId).map(LaboratoryMapper.INSTANCE::toModel);
    }

    @Override
    public void deleteById(UUID labId) {
        laboratoryRepository.deleteById(labId);
    }

    @Override
    public void deleteAll() {
        laboratoryRepository.deleteAll();
    }

    @Override
    public List<Laboratory> findLabsByType(LabType labType) {
        return laboratoryRepository.findByLabType(labType)
                .stream()
                .map(LaboratoryMapper.INSTANCE::toModel)
                .toList();
    }
}
