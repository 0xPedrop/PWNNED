package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.domain.enums.LabType;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.port.input.LaboratoryControllerPort;
import com.pwnned.port.input.LaboratoryServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/labs")
@RestController
public class LaboratoryController implements LaboratoryControllerPort {

    private final LaboratoryServicePort laboratoryServicePort;

    public LaboratoryController(LaboratoryServicePort laboratoryServicePort) {
        this.laboratoryServicePort = laboratoryServicePort;
    }

    @Override
    @PostMapping
    public ResponseEntity<LaboratoryDTO> createLab(LaboratoryDTO labDTO) {
        Laboratory lab = LaboratoryMapper.INSTANCE.toModel(labDTO);
        Laboratory createdLab = laboratoryServicePort.createLab(lab);
        LaboratoryDTO createdLabDTO = LaboratoryMapper.INSTANCE.toDTO(createdLab);
        return ResponseEntity.ok(createdLabDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LaboratoryDTO>> getAllLabs() {
        List<LaboratoryDTO> labsDTO = laboratoryServicePort.getAllLabs()
                .stream()
                .map(LaboratoryMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(labsDTO);
    }

    @Override
    @GetMapping("/{labId}")
    public ResponseEntity<LaboratoryDTO> getSingleLab(@PathVariable UUID labId) {
        return laboratoryServicePort.getSingleLab(labId)
                .map(lab -> ResponseEntity.ok(LaboratoryMapper.INSTANCE.toDTO(lab)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{labId}")
    public ResponseEntity<String> deleteLab(@PathVariable UUID labId) {
        laboratoryServicePort.deleteLab(labId);
        return ResponseEntity.ok("Lab " + labId + " deleted");
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteAllLabs() {
        laboratoryServicePort.deleteAllLabs();
        return ResponseEntity.ok("All Labs Deleted");
    }

    @Override
    @GetMapping("/type/{labType}")
    public ResponseEntity<List<LaboratoryDTO>> getLabsByType(LabType labType) {
        List<LaboratoryDTO> labsDTO = laboratoryServicePort.getLabsByType(labType)
                .stream()
                .map(LaboratoryMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(labsDTO);
    }
}
