package com.pwnned.adapter.output.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
