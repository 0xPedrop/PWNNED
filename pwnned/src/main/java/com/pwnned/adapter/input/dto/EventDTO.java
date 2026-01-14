package com.pwnned.adapter.input.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record EventDTO(
        UUID eventId,
        String name,
        String description,
        String type,
        LocalDateTime eventDate,
        double latitude,
        double longitude,
        Double distanceToUser
) {}