package ru.eremin.restwithspringjpa.mapper;

import org.mapstruct.Mapper;
import ru.eremin.restwithspringjpa.model.User;
import ru.eremin.restwithspringjpa.model.dto.UserDTO;


@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<User, UserDTO> {

    @Override
    User toEntity(UserDTO userDTO);

    @Override
    UserDTO toDto(User user);
}
