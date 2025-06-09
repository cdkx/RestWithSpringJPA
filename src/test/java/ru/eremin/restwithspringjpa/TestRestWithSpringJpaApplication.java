package ru.eremin.restwithspringjpa;

import org.springframework.boot.SpringApplication;

public class TestRestWithSpringJpaApplication {

    public static void main(String[] args) {
        SpringApplication.from(RestWithSpringJpaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
