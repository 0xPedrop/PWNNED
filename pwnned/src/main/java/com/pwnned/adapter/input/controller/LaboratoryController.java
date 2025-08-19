package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.port.input.LaboratoryControllerPort;
import com.pwnned.port.input.LaboratoryServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<LaboratoryDTO> createLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) {
        Laboratory laboratory = LaboratoryMapper.INSTANCE.toModel(laboratoryDTO);
        Laboratory createdLaboratory = laboratoryServicePort.createLaboratory(laboratory);
        LaboratoryDTO createdLaboratoryDTO = LaboratoryMapper.INSTANCE.toDTO(createdLaboratory);
        return ResponseEntity.ok(createdLaboratoryDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LaboratoryDTO>> getAllLaboratories() {
        List<LaboratoryDTO> labsDTO = laboratoryServicePort.getAllLaboratories()
                .stream()
                .map(LaboratoryMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(labsDTO);
    }

    @Override
    @GetMapping("/{labId}")
    public ResponseEntity<LaboratoryDTO> getSingleLaboratory(@PathVariable UUID laboratoryId) {
        return laboratoryServicePort.getSingleLaboratory(laboratoryId)
                .map(lab -> ResponseEntity.ok(LaboratoryMapper.INSTANCE.toDTO(lab)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{labId}")
    public ResponseEntity<String> deleteLaboratory(@PathVariable UUID laboratoryId) {
        laboratoryServicePort.deleteLaboratory(laboratoryId);
        return ResponseEntity.ok("Lab " + laboratoryId + " deleted");
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteAllLaboratories() {
        laboratoryServicePort.deleteAllLaboratories();
        return ResponseEntity.ok("All Labs Deleted");
    }

    @Override
    @GetMapping("/type/{labType}")
    public ResponseEntity<List<LaboratoryDTO>> getLaboratoriesByType(@PathVariable LaboratoryType laboratoryType) {
        List<LaboratoryDTO> labsDTO = laboratoryServicePort.getLaboratoriesByType(laboratoryType)
                .stream()
                .map(LaboratoryMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(labsDTO);
    }
}
