package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.LearningPathDTO;
import com.pwnned.adapter.input.dto.PageableDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.input.mapper.LearningPathMapper;
import com.pwnned.adapter.input.mapper.PageableMapper;
import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.LaboratoryControllerPort;
import com.pwnned.port.input.LaboratoryServicePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/labs")
@RestController
public class LaboratoryController implements LaboratoryControllerPort {

    private final LaboratoryServicePort laboratoryServicePort;
    private final LaboratoryMapper laboratoryMapper;
    private final PageableMapper pageableMapper;

    public LaboratoryController(LaboratoryServicePort laboratoryServicePort, LaboratoryMapper laboratoryMapper, PageableMapper pageableMapper) {
        this.laboratoryServicePort = laboratoryServicePort;
        this.laboratoryMapper = laboratoryMapper;
        this.pageableMapper = pageableMapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<LaboratoryDTO> createLaboratory(@Valid @RequestBody LaboratoryDTO laboratoryDTO) {
        Laboratory createdLaboratory = laboratoryServicePort.createLaboratory(laboratoryDTO);

        LaboratoryDTO createdLaboratoryDTO = laboratoryMapper.toDTO(createdLaboratory);
        return ResponseEntity.status(201).body(createdLaboratoryDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<PageableDTO> getAllLaboratories(@PageableDefault(size = 5, sort = "title") Pageable pageable) {
        Page<LaboratoryDTO> laboratoryDTOS = laboratoryServicePort.getAllLaboratories(pageable)
                .map(laboratoryMapper::toDTO);
        return ResponseEntity.ok(pageableMapper.toDTO(laboratoryDTOS));
    }

    @Override
    @GetMapping("/{laboratoryId}")
    public ResponseEntity<LaboratoryDTO> getSingleLaboratory(@PathVariable Long laboratoryId) {
        Laboratory laboratory = laboratoryServicePort.getSingleLaboratory(laboratoryId);
        return ResponseEntity.ok(laboratoryMapper.toDTO(laboratory));
    }

    @Override
    @DeleteMapping("/{laboratoryId}")
    public ResponseEntity<String> deleteLaboratory(@PathVariable Long laboratoryId) {
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
    @GetMapping("/type/{laboratoryType}")
    public ResponseEntity<List<LaboratoryDTO>> getLaboratoriesByType(@PathVariable LaboratoryType laboratoryType) {
        List<LaboratoryDTO> labsDTO = laboratoryServicePort.getLaboratoriesByType(laboratoryType)
                .stream()
                .map(laboratoryMapper::toDTO)
                .toList();
        return ResponseEntity.ok(labsDTO);
    }
}
