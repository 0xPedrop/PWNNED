package com.pwnned.port.input;

import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.model.Laboratory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LaboratoryServicePort {
    Laboratory createLaboratory(Laboratory laboratory);
    List<Laboratory> getAllLaboratories();
    Laboratory getSingleLaboratory(UUID laboratoryId);
    void deleteLaboratory(UUID laboratoryId);
    void deleteAllLaboratories();
    List<Laboratory> getLaboratoriesByType(LaboratoryType laboratoryType);
    List<Laboratory> getLaboratoriesByLearningPathId(UUID learningPathId);
}
