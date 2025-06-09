package ru.eremin.restwithspringjpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.eremin.restwithspringjpa.model.User;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select u from User u where u.email = :email")
    Optional<User> findUserByEmail(String email);
}
