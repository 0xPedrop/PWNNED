package com.pwnned.adapter.input.dto;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record EventDTO(
        Long eventId,
        String name,
        String description,
        String type,
        LocalDateTime eventDate,
        double latitude,
        double longitude,
        Double distanceToUser
) {}