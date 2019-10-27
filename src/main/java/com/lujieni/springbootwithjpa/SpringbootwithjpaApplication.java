package com.lujieni.springbootwithjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootwithjpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootwithjpaApplication.class, args);
    }

}
