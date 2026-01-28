package com.pwnned.adapter.output.jpa.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {

    @Id
    @Column(name = "event_id")
    private Long eventId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String type;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point geometry;
}