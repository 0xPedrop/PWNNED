package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findByUserType(UserType userType);
}
