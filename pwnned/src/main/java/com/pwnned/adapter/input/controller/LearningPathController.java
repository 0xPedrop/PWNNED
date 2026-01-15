package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.LearningPathDTO;
import com.pwnned.adapter.input.dto.PageableDTO;
import com.pwnned.adapter.input.mapper.LaboratoryMapper;
import com.pwnned.adapter.input.mapper.LearningPathMapper;
import com.pwnned.adapter.input.mapper.PageableMapper;
import com.pwnned.domain.enums.Difficulty;
import com.pwnned.domain.model.Laboratory;
import com.pwnned.domain.model.LearningPath;
import com.pwnned.port.input.LaboratoryServicePort;
import com.pwnned.port.input.LearningPathControllerPort;
import com.pwnned.port.input.LearningPathServicePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("api/v1/learningpaths")
@RestController
public class LearningPathController implements LearningPathControllerPort {

    private final LearningPathServicePort learningPathServicePort;
    private final LaboratoryServicePort laboratoryServicePort;
    private final LearningPathMapper learningPathMapper;
    private final LaboratoryMapper laboratoryMapper;
    private final PageableMapper pageableMapper;

    public LearningPathController(LearningPathServicePort learningPathServicePort,
                                  LaboratoryServicePort laboratoryServicePort,
                                  LearningPathMapper learningPathMapper,
                                  LaboratoryMapper laboratoryMapper,
                                  PageableMapper pageableMapper) {
        this.learningPathServicePort = learningPathServicePort;
        this.laboratoryServicePort = laboratoryServicePort;
        this.learningPathMapper = learningPathMapper;
        this.laboratoryMapper = laboratoryMapper;
        this.pageableMapper = pageableMapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<LearningPathDTO> createLearningPath(@Valid @RequestBody LearningPathDTO learningPathDTO) {
        LearningPath learningPath = learningPathMapper.toModel(learningPathDTO);
        LearningPath createdLearningPath = learningPathServicePort.createLearningPath(learningPath);
        LearningPathDTO createdLearningPathDTO = learningPathMapper.toDTO(createdLearningPath);
        return ResponseEntity.status(201).body(createdLearningPathDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<PageableDTO> getAllLearningPaths(@PageableDefault(size = 5, sort = "title") Pageable pageable) {
        Page<LearningPathDTO> learningPathDTOS = learningPathServicePort.getAllLearningPaths(pageable)
                .map(learningPathMapper::toDTO);
        return ResponseEntity.ok(pageableMapper.toDTO(learningPathDTOS));
    }

    @Override
    @GetMapping("/{learningPathId}")
    public ResponseEntity<LearningPathDTO> getSingleLearningPath(@PathVariable Long learningPathId) {
        LearningPath learningPath = learningPathServicePort.getSingleLearningPath(learningPathId);
        return ResponseEntity.ok(learningPathMapper.toDTO(learningPath));
    }

    @Override
    @DeleteMapping("/{learningPathId}")
    public ResponseEntity<String> deleteLearningPath(@PathVariable Long learningPathId) {
        learningPathServicePort.deleteLearningPath(learningPathId);
        return ResponseEntity.ok("Learning Path " + learningPathId + " deleted");
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteAllLearningPaths(Pageable pageable) {
        learningPathServicePort.deleteAllLearningPaths(pageable);
        return ResponseEntity.ok("All Learning Paths Deleted");
    }

    @Override
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<LearningPathDTO>> getLearningPathsByDifficulty(@PathVariable Difficulty difficulty) {
        List<LearningPathDTO> learningPathDTOS = learningPathServicePort.getLearningPathsByDifficulty(difficulty)
                .stream()
                .map(learningPathMapper::toDTO)
                .toList();
        return ResponseEntity.ok(learningPathDTOS);
    }

    @GetMapping("/{learningPathId}/labs")
    public ResponseEntity<List<LaboratoryDTO>> getLaboratoriesForLearningPath(
            @PathVariable Long learningPathId) {
        List<Laboratory> laboratories = laboratoryServicePort.getLaboratoriesByLearningPathId(learningPathId);
        List<LaboratoryDTO> laboratoryDTOs = laboratories.stream()
                .map(laboratoryMapper::toDTO)
                .toList();
        return ResponseEntity.ok(laboratoryDTOs);
    }
}
