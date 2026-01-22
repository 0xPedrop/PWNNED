package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.domain.enums.UserType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUserType(UserType userType);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Query(value = "SELECT calculate_user_xp(:userId)", nativeQuery = true)
    Integer getUserExperiencePoints(@Param("userId") Long userId);
}
