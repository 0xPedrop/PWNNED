package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.PageableDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.domain.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface UserControllerPort {
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO);
    ResponseEntity<PageableDTO> getAllUsers(Pageable pageable);
    ResponseEntity<UserDTO> getSingleUser(@PathVariable UUID userId);
    ResponseEntity<String> deleteUser(@PathVariable UUID userId);
    ResponseEntity<String> deleteAllUsers();
    ResponseEntity<String> promoteUser(UUID userId);
    ResponseEntity<List<UserDTO>> getUsersByType(@PathVariable UserType userType);
}
