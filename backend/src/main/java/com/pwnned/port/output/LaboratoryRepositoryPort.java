package com.pwnned.port.output;

import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.model.Laboratory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface LaboratoryRepositoryPort {
    Laboratory save(Laboratory laboratory);
    Page<Laboratory> findAll(Pageable pageable);
    Optional<Laboratory> findById(Long laboratoryId);
    List<Laboratory> getLaboratoriesByType(LaboratoryType laboratoryType);
    void deleteById(Long laboratoryId);
    void deleteAll();
    List<Laboratory> findByLearningPathId(Long learningPathId);
    void deleteAllByLearningPathId(Long learningPathId);
}
