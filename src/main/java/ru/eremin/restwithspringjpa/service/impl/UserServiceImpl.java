package ru.eremin.restwithspringjpa.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eremin.restwithspringjpa.exception.UserAlreadyExistsException;
import ru.eremin.restwithspringjpa.exception.UserDoesNotExistException;
import ru.eremin.restwithspringjpa.mapper.AbstractMapper;
import ru.eremin.restwithspringjpa.model.User;
import ru.eremin.restwithspringjpa.model.dto.UserDTO;
import ru.eremin.restwithspringjpa.repository.UserRepository;
import ru.eremin.restwithspringjpa.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AbstractMapper<User, UserDTO> userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO save(UserDTO userDTO) {
        if (userDTO.id() != null) {
            throw new IllegalArgumentException("id must be null for new user");
        }

        String email = userDTO.email();
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        User user = userMapper.toEntity(userDTO);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.error("id {} not found", id);
            throw new UserDoesNotExistException("User with id " + id + " not found");
        }

        User user = optionalUser.get();
        user.setAge(userDTO.age());
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.error("id {} not found", id);
            throw new UserDoesNotExistException("User with id " + id + " not found");
        }

        return userMapper.toDto(optionalUser.get());
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            list.add(userMapper.toDto(user));
        }

        return list;
    }

    @Override
    @Transactional
    public void delete(UserDTO userDTO) {
        Long id = userDTO.id();
        if (id == null) {
            log.error("id is null, user cant be found");
            throw new UserDoesNotExistException("id is null, user cant be found");
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.error("id {} not found", id);
            throw new UserDoesNotExistException("User with id " + id + " not found");
        }

        userRepository.delete(optionalUser.get());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("delete user by id {}", id);
        if (id < 0) {
            log.error("id {} not found", id);
            throw new UserDoesNotExistException("User with id " + id + " not found");
        }

        userRepository.deleteById(id);
    }
}
