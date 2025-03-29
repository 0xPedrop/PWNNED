package com.pwnned.port.input;

import com.pwnned.domain.enums.LabType;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LaboratoryServicePort {
    Laboratory createLab(Laboratory lab);
    List<Laboratory> getAllLabs();
    Optional<Laboratory> getSingleLab(UUID labId);
    void deleteLab(UUID labId);
    void deleteAllLabs();
    List<Laboratory> getLabsByType(LabType labType);
}
