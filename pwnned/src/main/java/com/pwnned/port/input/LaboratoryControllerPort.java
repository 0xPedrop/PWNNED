package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.PageableDTO;
import com.pwnned.domain.enums.LaboratoryType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LaboratoryControllerPort {
    ResponseEntity<LaboratoryDTO> createLaboratory(@RequestBody LaboratoryDTO laboratoryDTO);
    ResponseEntity<PageableDTO> getAllLaboratories(Pageable pageable);
    ResponseEntity<LaboratoryDTO> getSingleLaboratory(@PathVariable Long laboratoryId);
    ResponseEntity<String> deleteLaboratory(@PathVariable Long laboratoryId);
    ResponseEntity<String> deleteAllLaboratories();
    ResponseEntity<List<LaboratoryDTO>> getLaboratoriesByType(@PathVariable LaboratoryType laboratoryType);
}
