package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.output.jpa.repository.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query(value = "SELECT e.event_id as eventId, e.name as name, e.description as description, " +
            "e.type as type, e.event_date as eventDate, e.geometry as geometry, " +
            "ST_Distance(e.geometry::geography, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography) as distance " +
            "FROM events e WHERE " +
            "ST_DWithin(e.geometry::geography, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography, :distanceInMeters) " +
            "ORDER BY distance ASC",
            nativeQuery = true)
    List<EventDistanceProjection> findNearbyEventsWithDistance(@Param("lat") double lat,
                                                               @Param("lon") double lon,
                                                               @Param("distanceInMeters") double distanceInMeters);
}