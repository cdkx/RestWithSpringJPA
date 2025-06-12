package ru.eremin.restwithspringjpa.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eremin.restwithspringjpa.model.dto.UserDTO;
import ru.eremin.restwithspringjpa.service.UserService;

import java.util.List;

import static ru.eremin.restwithspringjpa.consts.WebConsts.*;


@AllArgsConstructor
@RestController
@RequestMapping(API + USERS)
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping(ID)
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        UserDTO foundUser = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @GetMapping(EMAILS + EMAIL)
    public ResponseEntity<UserDTO> findUserByEmail(@PathVariable String email) {
        UserDTO foundUser = userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @PutMapping(value = ID)
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        UserDTO savedUserDto = userService.update(userDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(savedUserDto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUserDto = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @DeleteMapping(ID)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
