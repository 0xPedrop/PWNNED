package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.LearningPathDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.input.mapper.LearningPathMapper;
import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.LearningPath;
import com.pwnned.domain.model.User;
import com.pwnned.domain.service.LaboratoryService;
import com.pwnned.port.input.LearningPathControllerPort;
import com.pwnned.port.input.LearningPathServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/learningpaths")
@RestController
public class LearningPathController implements LearningPathControllerPort {

    private final LearningPathServicePort learningPathServicePort;
    private final LaboratoryService laboratoryService;

    public LearningPathController(LearningPathServicePort learningPathServicePort, LaboratoryService laboratoryService) {
        this.learningPathServicePort = learningPathServicePort;
        this.laboratoryService = laboratoryService;
    }

    @Override
    @PostMapping
    public ResponseEntity<LearningPathDTO> createLearningPath(@RequestBody LearningPathDTO learningPathDTO) {
        LearningPath learningPath = LearningPathMapper.INSTANCE.toModel(learningPathDTO);
        LearningPath createdLearningPath = learningPathServicePort.createLearningPath(learningPath);
        LearningPathDTO createdLearningPathDTO = LearningPathMapper.INSTANCE.toDTO(createdLearningPath);
        return ResponseEntity.status(201).body(createdLearningPathDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<LearningPathDTO>> getAllLearningPaths() {
        List<LearningPathDTO> learningPathDTOS = learningPathServicePort.getAllLearningPaths().stream()
                .map(LearningPathMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(learningPathDTOS);
    }

    @Override
    @GetMapping("/{learningPathId}")
    public ResponseEntity<LearningPathDTO> getSingleLearningPath(@PathVariable UUID learningPathId) {
        LearningPath learningPath = learningPathServicePort.getSingleLearningPath(learningPathId);
        return ResponseEntity.ok(LearningPathMapper.INSTANCE.toDTO(learningPath));
    }

    @Override
    @DeleteMapping("/{learningPathId}")
    public ResponseEntity<String> deleteLearningPath(@PathVariable UUID learningPathId) {
        learningPathServicePort.deleteLearningPath(learningPathId);
        return ResponseEntity.ok("Learning Path " + learningPathId + " deleted");
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteAllLearningPaths() {
        learningPathServicePort.deleteAllLearningPaths();
        return ResponseEntity.ok("All Learning Paths Deleted");
    }

    @Override
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<LearningPathDTO>> getLearningPathsByDifficulty(@PathVariable Difficulty difficulty) {
        List<LearningPathDTO> learningPathDTOS = learningPathServicePort.getLearningPathsByDifficulty(difficulty)
                .stream()
                .map(LearningPathMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(learningPathDTOS);
    }

    @GetMapping("/{learningPathId}/labs")
    public ResponseEntity<List<LaboratoryDTO>> getLaboratoriesForLearningPath(
            @PathVariable UUID learningPathId) {
        List<Laboratory> laboratories = laboratoryService.getLaboratoriesByLearningPathId(learningPathId);
        List<LaboratoryDTO> laboratoryDTOs = laboratories.stream()
                .map(LaboratoryMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(laboratoryDTOs);
    }
}
