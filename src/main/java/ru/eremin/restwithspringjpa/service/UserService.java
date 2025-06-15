package ru.eremin.restwithspringjpa.service;

import ru.eremin.restwithspringjpa.model.dto.UserDTO;

import java.util.List;


public interface UserService {

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO, Long id);

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    List<UserDTO> findAll();

    void deleteById(Long id);
}
