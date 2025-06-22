package ru.eremin.restwithspringjpa.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eremin.restwithspringjpa.exception.UserAlreadyExistsException;
import ru.eremin.restwithspringjpa.exception.UserDoesNotExistException;
import ru.eremin.restwithspringjpa.kafka.KafkaProducer;
import ru.eremin.restwithspringjpa.mapper.AbstractMapper;
import ru.eremin.restwithspringjpa.model.User;
import ru.eremin.restwithspringjpa.model.dto.UserDTO;
import ru.eremin.restwithspringjpa.repository.UserRepository;
import ru.eremin.restwithspringjpa.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AbstractMapper<User, UserDTO> userMapper;
    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;
    public static final String USER_CREATED = "User created.";
    public static final String USER_DELETED = "User deleted.";

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
        user = userRepository.save(user);
        log.info("user saved {}", userDTO);

        kafkaProducer.sendMessage(email, USER_CREATED);
        log.info("message {} sent to kafka", email);

        return userMapper.toDto(user);
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
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            log.error("id {} not found", id);
            throw new UserDoesNotExistException("User with id " + id + " not found");
        }

        return userMapper.toDto(optionalUser.get());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            log.error("user with email {} not found", email);
            throw new UserDoesNotExistException("User with email " + email + " not found");
        }

        return userMapper.toDto(optionalUser.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("delete user by id {}", id);
        if (id < 0) {
            log.error("id {} not found", id);
            throw new UserDoesNotExistException("User with id " + id + " not found");
        }

        Optional<User> user = userRepository.findById(id);
        String email = "";
        if (user.isPresent()) {
            email = user.get().getEmail();
            log.info("email found {}", email);
        }

        userRepository.deleteById(id);
        if (!email.isEmpty()) {
            kafkaProducer.sendMessage(email, USER_DELETED);
            log.info("message {} sent to kafka", email);
        }
    }
}
