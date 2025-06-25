package ru.eremin.restwithspringjpa.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "User data transfer object")
public record UserDTO(@Schema(example = "1", description = "User ID") Long id,
                      @Schema(example = "Vasya Petrov", description = "Full name") String name,
                      @Schema(example = "Vasya@example.com", description = "Email address") String email,
                      @Schema(example = "30", description = "Age of the user") int age) {

}
