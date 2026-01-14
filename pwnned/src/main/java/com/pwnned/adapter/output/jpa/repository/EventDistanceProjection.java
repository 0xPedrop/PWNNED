package com.pwnned.adapter.output.jpa.repository;

import java.util.UUID;

public interface EventDistanceProjection {
    Long getEventId();
    String getName();
    String getDescription();
    String getType();
    java.time.LocalDateTime getEventDate();
    Object getGeometry();
    Double getDistance();
}