package com.pwnned.domain.service;

import com.pwnned.domain.enums.LabType;
import com.pwnned.domain.exception.LaboratoryNotFoundException;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.port.input.LaboratoryServicePort;
import com.pwnned.port.output.LaboratoryRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaboratoryService implements LaboratoryServicePort {

   private final LaboratoryRepositoryPort laboratoryRepositoryPort;

    public LaboratoryService(LaboratoryRepositoryPort laboratoryRepositoryPort) {
        this.laboratoryRepositoryPort = laboratoryRepositoryPort;
    }

    @Override
    public Laboratory createLab(Laboratory lab) {
        return laboratoryRepositoryPort.save(lab);
    }

    @Override
    public List<Laboratory> getAllLabs() {
        List<Laboratory> labs = laboratoryRepositoryPort.findAll();
        if (labs.isEmpty()) throw new LaboratoryNotFoundException("Labs Not Found");
        return labs;
    }

    @Override
    public Optional<Laboratory> getSingleLab(UUID labId) {
        Optional<Laboratory> lab = laboratoryRepositoryPort.findById(labId);
        if (lab.isEmpty()) throw new LaboratoryNotFoundException("Lab " + labId + " Not Found");
        return lab;
    }

    @Override
    public void deleteLab(UUID labId) {
        Optional<Laboratory> lab = laboratoryRepositoryPort.findById(labId);
        if (lab.isEmpty()) throw new LaboratoryNotFoundException("Lab " + labId + " Not Found");
        laboratoryRepositoryPort.deleteById(labId);
    }

    @Override
    public void deleteAllLabs() {
        laboratoryRepositoryPort.deleteAll();
    }

    @Override
    public List<Laboratory> getLabsByType(LabType labType) {
        List<Laboratory> labs = laboratoryRepositoryPort.findLabsByType(labType);
        if (labs.isEmpty()) throw new LaboratoryNotFoundException("Labs Not Found");
        return labs;
    }
}
