package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.LearningPathDTO;
import com.pwnned.adapter.input.dto.PageableDTO;
import com.pwnned.domain.enums.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface LearningPathControllerPort {
    ResponseEntity<LearningPathDTO> createLearningPath(@RequestBody LearningPathDTO learningPathDTO);
    ResponseEntity<PageableDTO> getAllLearningPaths(Pageable pageable);
    ResponseEntity<LearningPathDTO> getSingleLearningPath(@PathVariable UUID learningPathId);
    ResponseEntity<String> deleteLearningPath(@PathVariable UUID learningPathId);
    ResponseEntity<String> deleteAllLearningPaths(Pageable pageable);
    ResponseEntity<List<LearningPathDTO>> getLearningPathsByDifficulty(@PathVariable Difficulty difficulty);
    ResponseEntity<List<LaboratoryDTO>> getLaboratoriesForLearningPath(
            @PathVariable UUID learningPathId);
}
