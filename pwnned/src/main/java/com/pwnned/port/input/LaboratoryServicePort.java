package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.model.Laboratory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LaboratoryServicePort {
    Laboratory createLaboratory(LaboratoryDTO laboratoryDTO);
    Page<Laboratory> getAllLaboratories(Pageable pageable);
    Laboratory getSingleLaboratory(UUID laboratoryId);
    void deleteLaboratory(UUID laboratoryId);
    void deleteAllLaboratories();
    List<Laboratory> getLaboratoriesByType(LaboratoryType laboratoryType);
    List<Laboratory> getLaboratoriesByLearningPathId(UUID learningPathId);
}
