package com.pwnned.port.output;

import com.pwnned.domain.model.Event;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> findById(Long eventId);
    List<Event> findAll();
    List<Event> findNearbyEvents(double lat, double lon, double distanceInMeters);
}