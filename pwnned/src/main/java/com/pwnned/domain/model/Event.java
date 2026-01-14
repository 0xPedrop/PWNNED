package com.pwnned.domain.model;

import org.locationtech.jts.geom.Point;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Event {
    private UUID eventId;
    private String name;
    private String description;
    private String type;
    private LocalDateTime eventDate;
    private Point geometry;
    private Double distanceToUser;

    public Event() {
    }

    public Event(UUID eventId, String name, String description, String type, LocalDateTime eventDate, Point geometry,
                 Double distanceToUser) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.eventDate = eventDate;
        this.geometry = geometry;
        this.distanceToUser = distanceToUser;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public Point getGeometry() {
        return geometry;
    }

    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }

    public Double getDistanceToUser() {
        return distanceToUser;
    }

    public void setDistanceToUser(Double distanceToUser) {
        this.distanceToUser = distanceToUser;
    }

    public boolean isPastEvent() {
        return eventDate.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventId);
    }
}