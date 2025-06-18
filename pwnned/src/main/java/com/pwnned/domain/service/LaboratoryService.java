package com.pwnned.domain.service;

import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.exception.LaboratoryNotFoundException;
import com.pwnned.domain.exception.LearningPathNotFoundException;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.port.input.LaboratoryServicePort;
import com.pwnned.port.output.LaboratoryRepositoryPort;
import com.pwnned.port.output.LearningPathRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaboratoryService implements LaboratoryServicePort {

    private final LaboratoryRepositoryPort laboratoryRepositoryPort;
    private final LearningPathRepositoryPort learningPathRepositoryPort;

    public LaboratoryService(LaboratoryRepositoryPort laboratoryRepositoryPort, LearningPathRepositoryPort learningPathRepositoryPort) {
        this.laboratoryRepositoryPort = laboratoryRepositoryPort;
        this.learningPathRepositoryPort = learningPathRepositoryPort;
    }

    @Override
    public Laboratory createLaboratory(Laboratory laboratory) {
        return laboratoryRepositoryPort.save(laboratory);
    }

    @Override
    public List<Laboratory> getAllLaboratories() {
        List<Laboratory> laboratories = laboratoryRepositoryPort.findAll();
        if (laboratories.isEmpty()) throw new LaboratoryNotFoundException("Laboratories Not Found");
        return laboratories;
    }

    @Override
    public Optional<Laboratory> getSingleLaboratory(UUID laboratoryId) {
        Optional<Laboratory> laboratory = laboratoryRepositoryPort.findById(laboratoryId);
        if (laboratory.isEmpty()) throw new LaboratoryNotFoundException("Laboratory "
                + laboratoryId + " Not Found");
        return laboratory;
    }

    @Override
    public void deleteLaboratory(UUID laboratoryId) {
        Optional<Laboratory> laboratory = laboratoryRepositoryPort.findById(laboratoryId);
        if (laboratory.isEmpty()) throw new LaboratoryNotFoundException("Laboratory "
                + laboratoryId + " Not Found");
        laboratoryRepositoryPort.deleteById(laboratoryId);
    }

    @Override
    public void deleteAllLaboratories() {
        laboratoryRepositoryPort.deleteAll();
    }

    @Override
    public List<Laboratory> getLaboratoriesByType(LaboratoryType laboratoryType) {
        List<Laboratory> laboratories = laboratoryRepositoryPort.getLaboratoriesByType(laboratoryType);
        if (laboratories.isEmpty()) throw new LaboratoryNotFoundException("Laboratories Not Found");
        return laboratories;
    }

    public List<Laboratory> getLaboratoriesByLearningPathId(UUID learningPathId) {
        learningPathRepositoryPort.findById(learningPathId)
                .orElseThrow(() -> new LearningPathNotFoundException("Learning Path with ID " + learningPathId + " not found."));

        return laboratoryRepositoryPort.findByLearningPathId(learningPathId);
    }
}
