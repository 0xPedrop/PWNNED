package com.pwnned.port.output;

import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Page<User> findAll(Pageable pageable);
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    void deleteById(Long userId);
    void deleteAll();
    List<User> getUsersByType(UserType userType);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Integer getUserExperiencePoints(Long userId);
}
