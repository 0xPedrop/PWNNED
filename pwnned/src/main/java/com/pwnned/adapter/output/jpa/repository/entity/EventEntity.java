package com.pwnned.adapter.output.jpa.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID eventId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String type;

    private LocalDateTime eventDate;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point geometry;
}