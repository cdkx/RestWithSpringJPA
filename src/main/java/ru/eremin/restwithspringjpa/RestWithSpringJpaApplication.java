package ru.eremin.restwithspringjpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

import java.util.Date;


@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class RestWithSpringJpaApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RestWithSpringJpaApplication.class, args);
        log.info("app UserService started on {}", new Date(context.getStartupDate()));
    }
}
