package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.domain.enums.LabType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface LaboratoryControllerPort {
    ResponseEntity<LaboratoryDTO> createLab(@RequestBody LaboratoryDTO labDTO);
    ResponseEntity<List<LaboratoryDTO>> getAllLabs();
    ResponseEntity<LaboratoryDTO> getSingleLab(@PathVariable UUID labId);
    ResponseEntity<String> deleteLab(@PathVariable UUID labId);
    ResponseEntity<String> deleteAllLabs();
    ResponseEntity<List<LaboratoryDTO>> getLabsByType(@PathVariable LabType labType);
}
