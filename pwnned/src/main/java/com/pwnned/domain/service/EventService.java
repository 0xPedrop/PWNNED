package com.pwnned.domain.service;

import com.pwnned.adapter.input.dto.EventDTO;
import com.pwnned.adapter.input.mapper.EventMapper;
import com.pwnned.adapter.output.jpa.repository.util.SnowflakeIdGenerator;
import com.pwnned.adapter.output.redis.EventRedisAdapter;
import com.pwnned.domain.model.Event;
import com.pwnned.port.input.EventServicePort;
import com.pwnned.port.output.EventRepositoryPort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService implements EventServicePort {

    private final EventRepositoryPort eventRepositoryPort;
    private final EventMapper eventMapper;
    private final EventRedisAdapter eventRedisAdapter;
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    public EventService(EventRepositoryPort eventRepositoryPort,
                        EventMapper eventMapper,
                        EventRedisAdapter eventRedisAdapter, SnowflakeIdGenerator snowflakeIdGenerator) {
        this.eventRepositoryPort = eventRepositoryPort;
        this.eventMapper = eventMapper;
        this.eventRedisAdapter = eventRedisAdapter;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = eventMapper.toModel(eventDTO);
        event.setEventId(snowflakeIdGenerator.nextId());

        Event savedEvent = eventRepositoryPort.save(event);
        EventDTO savedDTO = eventMapper.toDTO(savedEvent);

        eventRedisAdapter.cacheEvent(savedDTO);
        return savedDTO;
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
    public EventDTO getEventById(Long eventId) {
        return eventRedisAdapter.getCachedEvent(eventId)
                .orElseGet(() -> {
                    EventDTO dto = eventRepositoryPort.findById(eventId)
                            .map(eventMapper::toDTO)
                            .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
                    eventRedisAdapter.cacheEvent(dto);
                    return dto;
                });
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRedisAdapter.getCachedAllEvents()
                .orElseGet(() -> {
                    List<EventDTO> list = eventRepositoryPort.findAll()
                            .stream()
                            .map(eventMapper::toDTO)
                            .toList();
                    eventRedisAdapter.cacheAllEvents(list);
                    return list;
                });
    }
}