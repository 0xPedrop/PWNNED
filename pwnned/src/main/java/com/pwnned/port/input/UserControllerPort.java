package com.pwnned.port.input;

import com.pwnned.adapter.input.dto.PageableDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.domain.enums.UserType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface UserControllerPort {
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO);
    ResponseEntity<PageableDTO> getAllUsers(Pageable pageable);
    ResponseEntity<UserDTO> getSingleUser(@PathVariable Long userId);
    ResponseEntity<String> deleteUser(@PathVariable Long userId);
    ResponseEntity<String> deleteAllUsers(Pageable pageable);
    ResponseEntity<String> promoteUser(Long userId);
    ResponseEntity<List<UserDTO>> getUsersByType(@PathVariable UserType userType);
    ResponseEntity<String> uploadPhoto(String userId, MultipartFile file) throws Exception;
}
