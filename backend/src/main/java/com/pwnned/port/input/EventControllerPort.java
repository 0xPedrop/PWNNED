package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.EventDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface EventControllerPort {
    ResponseEntity<EventDTO> create(EventDTO dto);
    ResponseEntity<List<EventDTO>> listAll();
    ResponseEntity<List<EventDTO>> getNearby(double lat, double lon, double radius);
    ResponseEntity<EventDTO> getById(Long eventId);
}