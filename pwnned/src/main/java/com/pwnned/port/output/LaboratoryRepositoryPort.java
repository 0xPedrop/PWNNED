package com.pwnned.port.output;

import com.pwnned.domain.enums.LabType;
import com.pwnned.domain.model.Laboratory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LaboratoryRepositoryPort {
    Laboratory save(Laboratory lab);
    List<Laboratory> findAll();
    Optional<Laboratory> findById(UUID labId);
    void deleteById(UUID labId);
    void deleteAll();
    List<Laboratory> findLabsByType(LabType labType);
}
