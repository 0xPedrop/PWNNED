package com.pwnned.adapter.input.mapper;

import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import com.pwnned.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDTO userDTO);
    User toModel(UserEntity userEntity);

    UserDTO toDTO(User user);
    UserEntity toEntity(User user);



}
