package com.pwnned.domain.service;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.output.redis.LaboratoryRedisAdapter;
import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.exception.LaboratoryNotFoundException;
import com.pwnned.domain.exception.LearningPathNotFoundException;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.LearningPath;
import com.pwnned.port.input.LaboratoryServicePort;
import com.pwnned.port.output.LaboratoryRepositoryPort;
import com.pwnned.port.output.LearningPathRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaboratoryService implements LaboratoryServicePort {

    private final LaboratoryRepositoryPort laboratoryRepositoryPort;
    private final LaboratoryRedisAdapter  laboratoryRedisAdapter;
    private final LearningPathRepositoryPort learningPathRepositoryPort;

    public LaboratoryService(LaboratoryRepositoryPort laboratoryRepositoryPort,
                             LaboratoryRedisAdapter laboratoryRedisAdapter,
                             LearningPathRepositoryPort learningPathRepositoryPort) {
        this.laboratoryRepositoryPort = laboratoryRepositoryPort;
        this.laboratoryRedisAdapter = laboratoryRedisAdapter;
        this.learningPathRepositoryPort = learningPathRepositoryPort;
    }

    @Override
    public Laboratory createLaboratory(LaboratoryDTO laboratoryDTO) {
        LearningPath learningPath = learningPathRepositoryPort.findById(laboratoryDTO.learningPathId())
                .orElseThrow(() -> new LearningPathNotFoundException("Learning Path com o ID "
                        + laboratoryDTO.learningPathId() + " não foi encontrado."));

        Laboratory newLaboratory = new Laboratory();
        newLaboratory.setTitle(laboratoryDTO.title());
        newLaboratory.setDifficulty(laboratoryDTO.difficulty());
        newLaboratory.setLaboratoryType(laboratoryDTO.laboratoryType());

        newLaboratory.setLearningPath(learningPath);

        return laboratoryRepositoryPort.save(newLaboratory);
    }

    @Override
    public Page<Laboratory> getAllLaboratories(Pageable pageable) {
        return laboratoryRepositoryPort.findAll(pageable);
    }

    @Override
    public Laboratory getSingleLaboratory(UUID laboratoryId) {
        Optional<Laboratory> cachedLaboratory = laboratoryRedisAdapter.getCachedLaboratory(laboratoryId);

        if (cachedLaboratory.isPresent()) {
            return cachedLaboratory.get();
        }

        Laboratory laboratory = laboratoryRepositoryPort.findById(laboratoryId)
                .orElseThrow(() -> new LaboratoryNotFoundException("Laboratory not found with ID: " + laboratoryId));

        laboratoryRedisAdapter.cacheLaboratory(laboratory);
        return laboratory;
    }

    @Override
    public void deleteLaboratory(UUID laboratoryId) {
        Optional<Laboratory> laboratory = laboratoryRepositoryPort.findById(laboratoryId);
        if (laboratory.isEmpty()) throw new LaboratoryNotFoundException("Laboratory "
                + laboratoryId + " Not Found");
        laboratoryRepositoryPort.deleteById(laboratoryId);
        laboratoryRedisAdapter.invalidateCacheForLaboratory(laboratoryId);
    }

    @Override
    public void deleteAllLaboratories() {
        laboratoryRepositoryPort.deleteAll();
        laboratoryRedisAdapter.getCachedLaboratoriesByType(LaboratoryType.STANDART.name());
        laboratoryRedisAdapter.getCachedLaboratoriesByType(LaboratoryType.SPECIALIZED.name());
    }

    @Override
    public List<Laboratory> getLaboratoriesByType(LaboratoryType laboratoryType) {
        Optional<List<Laboratory>> cachedLaboratoriesByType = laboratoryRedisAdapter
                .getCachedLaboratoriesByType(laboratoryType.name());

        if (cachedLaboratoriesByType.isPresent()) {
            return cachedLaboratoriesByType.get();
        }

        List<Laboratory> laboratories = laboratoryRepositoryPort.getLaboratoriesByType(laboratoryType);
        if (laboratories.isEmpty()) throw new LaboratoryNotFoundException("Laboratories Not Found");

        laboratoryRedisAdapter.cacheLaboratoryByType(laboratoryType.name(), laboratories);
        return laboratories;
    }

    public List<Laboratory> getLaboratoriesByLearningPathId(UUID learningPathId) {
        Optional<List<Laboratory>> cachedLabs = laboratoryRedisAdapter
                .getCachedLaboratoriesByLearningPathId(learningPathId);

        if (cachedLabs.isPresent()) {
            return cachedLabs.get();
        }

        learningPathRepositoryPort.findById(learningPathId)
                .orElseThrow(() -> new LearningPathNotFoundException("Learning Path with ID " + learningPathId
                        + " not found."));

        List<Laboratory> laboratories = laboratoryRepositoryPort.findByLearningPathId(learningPathId);
        if (laboratories.isEmpty()) {
            throw new LaboratoryNotFoundException("Laboratories not found for Learning Path with ID: " + learningPathId);
        }

        laboratoryRedisAdapter.cacheLaboratoriesByLearningPathId(learningPathId, laboratories);
        return laboratories;
    }
}
