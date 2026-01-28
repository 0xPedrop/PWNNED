package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.EventDTO;
import com.pwnned.port.input.EventControllerPort;
import com.pwnned.port.input.EventServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController implements EventControllerPort {

    private final EventServicePort eventServicePort;

    public EventController(EventServicePort eventServicePort) {
        this.eventServicePort = eventServicePort;
    }

    @Override
    @PostMapping
    public ResponseEntity<EventDTO> create(@RequestBody EventDTO dto) {
        EventDTO created = eventServicePort.createEvent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EventDTO>> listAll() {
        return ResponseEntity.ok(eventServicePort.getAllEvents());
    }

    @Override
    @GetMapping("/nearby")
    public ResponseEntity<List<EventDTO>> getNearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "100") double radius) {

        List<EventDTO> nearbyEvents = eventServicePort.getNearbyEvents(lat, lon, radius);
        return ResponseEntity.ok(nearbyEvents);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventServicePort.getEventById(id));
    }
}