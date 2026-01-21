package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.EventDTO;
import java.util.List;

public interface EventServicePort {
    EventDTO createEvent(EventDTO eventDTO);
    List<EventDTO> getNearbyEvents(double lat, double lon, double radiusInKm);
    EventDTO getEventById(Long eventId);
    List<EventDTO> getAllEvents();
}