package com.pwnned.adapter.input.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import com.pwnned.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDTO userDTO);
    User toModel(UserEntity userEntity);

    UserDTO toDTO(User user);
    UserEntity toEntity(User user);


    default User toDomainFromMap(Map<String, Object> map) {
        try {
            return new ObjectMapper().convertValue(map, User.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing Order from map", e);
        }
    }
}
