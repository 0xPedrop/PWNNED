package com.pwnned.domain.service;

import com.pwnned.adapter.input.dto.EventDTO;
import com.pwnned.adapter.input.mapper.EventMapper;
import com.pwnned.domain.model.Event;
import com.pwnned.port.input.EventServicePort;
import com.pwnned.port.output.EventRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService implements EventServicePort {

    private final EventRepositoryPort eventRepositoryPort;
    private final EventMapper eventMapper;

    public EventService(EventRepositoryPort eventRepositoryPort, EventMapper eventMapper) {
        this.eventRepositoryPort = eventRepositoryPort;
        this.eventMapper = eventMapper;
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = eventMapper.toModel(eventDTO);
        Event savedEvent = eventRepositoryPort.save(event);
        return eventMapper.toDTO(savedEvent);
    }

    @Override
    public List<EventDTO> getNearbyEvents(double lat, double lon, double radiusInKm) {
        double distanceInMeters = radiusInKm * 1000;

        return eventRepositoryPort.findNearbyEvents(lat, lon, distanceInMeters)
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(UUID id) {
        return eventRepositoryPort.findById(id)
                .map(eventMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepositoryPort.findAll()
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }
}