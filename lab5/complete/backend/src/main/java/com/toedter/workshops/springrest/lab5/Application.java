package com.toedter.workshops.springrest.lab5;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.toedter.spring.hateoas.jsonapi.JsonApiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
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
