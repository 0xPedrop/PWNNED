package com.pwnned.port.output;

import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LaboratoryRepositoryPort {
    Laboratory save(Laboratory laboratory);
    List<Laboratory> findAll();
    Optional<Laboratory> findById(UUID laboratoryId);
    List<Laboratory> getLaboratoriesByType(LaboratoryType laboratoryType);
    void deleteById(UUID laboratoryId);
    void deleteAll();
    List<Laboratory> findByLearningPathId(UUID learningPathId);
}
