package ru.eremin.restwithspringjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.eremin.restwithspringjpa.controller.rest.UserController;


@SpringBootApplication
public class RestWithSpringJpaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RestWithSpringJpaApplication.class, args);
        context.getBean(UserController.class).findUsers().getBody().forEach(System.out::println);
    }
}
