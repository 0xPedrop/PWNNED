package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.EventMapper;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.EventEntity;
import com.pwnned.domain.model.Event;
import com.pwnned.port.output.EventRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EventRepositoryAdapter implements EventRepositoryPort {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventRepositoryAdapter(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public Event save(Event event) {
        EventEntity entity = eventMapper.toEntity(event, new CycleAvoidingMappingContext());
        EventEntity savedEntity = eventRepository.save(entity);
        return eventMapper.toModel(savedEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public Optional<Event> findById(UUID id) {
        return eventRepository.findById(id)
                .map(entity -> eventMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll().stream()
                .map(entity -> eventMapper.toModel(entity, new CycleAvoidingMappingContext()))
                .toList();
    }

    @Override
    public List<Event> findNearbyEvents(double lat, double lon, double distanceInMeters) {
        List<EventDistanceProjection> results = eventRepository.findNearbyEventsWithDistance(lat, lon, distanceInMeters);

        return results.stream().map(proj -> {
            Event model = new Event();
            model.setEventId(proj.getEventId());
            model.setName(proj.getName());
            model.setDescription(proj.getDescription());
            model.setType(proj.getType());
            model.setEventDate(proj.getEventDate());

            Object geo = proj.getGeometry();
            if (geo instanceof org.locationtech.jts.geom.Point point) {
                model.setGeometry(point);
            } else if (geo instanceof org.geolatte.geom.Point geolattePoint) {
                org.locationtech.jts.geom.Coordinate coord = new org.locationtech.jts.geom.Coordinate(
                        geolattePoint.getPosition().getCoordinate(0),
                        geolattePoint.getPosition().getCoordinate(1)
                );
                model.setGeometry(new org.locationtech.jts.geom.GeometryFactory().createPoint(coord));
            }

            if (proj.getDistance() != null) {
                double km = proj.getDistance() / 1000.0;
                model.setDistanceToUser(Math.round(km * 100.0) / 100.0);
            }
            return model;
        }).toList();
    }
}