package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.LaboratoryDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.domain.enums.LaboratoryType;
import com.pwnned.domain.enums.UserType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface LaboratoryControllerPort {
    ResponseEntity<LaboratoryDTO> createLaboratory(@RequestBody LaboratoryDTO laboratoryDTO);

    ResponseEntity<List<LaboratoryDTO>> getAllLaboratories();

    ResponseEntity<LaboratoryDTO> getSingleLaboratory(@PathVariable UUID laboratoryId);

    ResponseEntity<String> deleteLaboratory(@PathVariable UUID laboratoryId);

    ResponseEntity<String> deleteAllLaboratories();

    ResponseEntity<List<LaboratoryDTO>> getLaboratoriesByType(@PathVariable LaboratoryType laboratoryType);
}
