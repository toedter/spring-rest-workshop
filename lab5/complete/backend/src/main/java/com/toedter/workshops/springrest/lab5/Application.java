package com.toedter.workshops.springrest.lab5;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.toedter.spring.hateoas.jsonapi.JsonApiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // when deployed as a docker container to Heroku
        // Heroku sets the PORT environment variable
        // The DYNO environment variable is just to make sure to run in an Heroku environment
        String herokuPort = System.getenv().get("PORT");
        String herokuDyno = System.getenv().get("DYNO");
        if (herokuPort != null && herokuDyno != null) {
            System.getProperties().put("server.port", herokuPort);
        }

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public JsonApiConfiguration jsonApiConfiguration() {
        return new JsonApiConfiguration()
                .withJsonApiVersionRendered(true)
                .withEmptyAttributesObjectSerialized(false)
                .withPageMetaAutomaticallyCreated(true)
                .withObjectMapperCustomizer(objectMapper -> {
                    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
                })
                .withAffordancesRenderedAsLinkMeta(JsonApiConfiguration.AffordanceType.SPRING_HATEOAS);
    }
}
