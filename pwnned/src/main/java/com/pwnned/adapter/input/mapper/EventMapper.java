package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.EventDTO;
import com.pwnned.adapter.output.jpa.repository.entity.EventEntity;
import com.pwnned.domain.model.Event;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EventMapper {

    GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    @Mapping(target = "geometria", source = ".", qualifiedByName = "coordsToPoint")
    Event toModel(EventDTO dto);

    Event toModel(EventEntity entity);

    @Mapping(target = "latitude", source = "geometria.y")
    @Mapping(target = "longitude", source = "geometria.x")
    @Mapping(target = "distanceToUser", source = "distanceToUser")
    EventDTO toDTO(Event domain);

    EventEntity toEntity(Event domain);

    @Named("coordsToPoint")
    default Point coordsToPoint(EventDTO dto) {
        if (dto == null) return null;
        return factory.createPoint(new Coordinate(dto.longitude(), dto.latitude()));
    }
}