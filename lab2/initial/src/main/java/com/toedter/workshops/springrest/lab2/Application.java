package com.toedter.workshops.springrest.lab2;

import com.toedter.workshops.springrest.lab2.user.UserTestDataLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean(initMethod = "loadData")
    UserTestDataLoader RepositoryTestData() {
        return new UserTestDataLoader();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
