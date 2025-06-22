package ru.eremin.restwithspringjpa.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eremin.restwithspringjpa.model.dto.UserDTO;
import ru.eremin.restwithspringjpa.service.UserService;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        UserDTO foundUser = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @GetMapping("/emails/{email}")
    public ResponseEntity<UserDTO> findUserByEmail(@PathVariable String email) {
        UserDTO foundUser = userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        UserDTO savedUserDto = userService.update(userDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(savedUserDto);
    }

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUserDto = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
