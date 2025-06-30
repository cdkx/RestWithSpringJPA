package ru.eremin.restwithspringjpa.model.dto;

import ru.eremin.restwithspringjpa.model.Operation;


public record PostDTO(String email, Operation operation) {

}
