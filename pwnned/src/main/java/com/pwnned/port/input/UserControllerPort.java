package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserControllerPort {
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO);
    ResponseEntity<List<UserDTO>> getAllUsers();
    ResponseEntity<UserDTO> getSingleUser(@PathVariable Long userId);
    ResponseEntity<String> deleteUser(@PathVariable Long userId);
    ResponseEntity<String> deleteAllUsers();
}
