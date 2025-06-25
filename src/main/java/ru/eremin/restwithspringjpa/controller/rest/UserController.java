package ru.eremin.restwithspringjpa.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eremin.restwithspringjpa.model.dto.UserDTO;
import ru.eremin.restwithspringjpa.service.UserService;

import java.util.List;


@Tag(name = "Users", description = "User management API")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;


    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "Users found")
    @GetMapping()
    public ResponseEntity<List<UserDTO>> findUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }


    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@Parameter(description = "ID of the user to get") @PathVariable Long id) {
        UserDTO foundUser = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }


    @Operation(summary = "Get user by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User does not exist")})
    @GetMapping("/emails/{email}")
    public ResponseEntity<UserDTO> findUserByEmail(@Parameter(description = "email of the user to get") @PathVariable String email) {
        UserDTO foundUser = userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }


    @Operation(summary = "Update user", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User object that needs to be updated",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))))
    @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @Parameter(description = "ID of the user to update") @PathVariable Long id) {
        UserDTO savedUserDto = userService.update(userDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(savedUserDto);
    }


    @Operation(summary = "Create user", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User object that needs to be created",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "User already exists"),})
    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUserDto = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }


    @Operation(summary = "Delete user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User does not exist")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
