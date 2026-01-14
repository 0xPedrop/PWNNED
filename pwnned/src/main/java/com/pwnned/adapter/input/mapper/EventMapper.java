package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.EventDTO;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.EventEntity;
import com.pwnned.domain.model.Event;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EventMapper {

    GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    @Mapping(target = "geometria", source = ".", qualifiedByName = "coordsToPoint")
    Event toModel(EventDTO eventDTO);

    Event toModel(EventEntity eventEntity, @Context CycleAvoidingMappingContext context);

    @Mapping(target = "latitude", source = "geometria.y")
    @Mapping(target = "longitude", source = "geometria.x")
    @Mapping(target = "distanceToUser", source = "distanceToUser")
    EventDTO toDTO(Event event);

    EventEntity toEntity(Event event, @Context CycleAvoidingMappingContext context);

    @Named("coordsToPoint")
    default Point coordsToPoint(EventDTO eventDTO) {
        if (eventDTO == null) return null;
        return factory.createPoint(new Coordinate(eventDTO.longitude(), eventDTO.latitude()));
    }
}