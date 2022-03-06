package com.toedter.workshops.springrest.lab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = { EnableHypermediaSupport.HypermediaType.HAL_FORMS })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
