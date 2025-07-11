package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.LaboratoryControllerPort;
import com.pwnned.port.input.LaboratoryServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/labs")
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
        return ResponseEntity.status(201).body(createdLaboratoryDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LaboratoryDTO>> getAllLaboratories() {
        List<LaboratoryDTO> laboratoryDTOS = laboratoryServicePort.getAllLaboratories().stream()
                .map(LaboratoryMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(laboratoryDTOS);
    }

    @Override
    @GetMapping("/{laboratoryId}")
    public ResponseEntity<LaboratoryDTO> getSingleLaboratory(@PathVariable UUID laboratoryId) {
        Laboratory laboratory = laboratoryServicePort.getSingleLaboratory(laboratoryId);
        return ResponseEntity.ok(LaboratoryMapper.INSTANCE.toDTO(laboratory));
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
