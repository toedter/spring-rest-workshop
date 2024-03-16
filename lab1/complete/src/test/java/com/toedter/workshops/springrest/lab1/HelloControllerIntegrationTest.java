package com.toedter.workshops.springrest.lab1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldGetGreeting() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
        assertThat(response.getBody()).isEqualTo(HelloController.LAB1_GREETINGS_FROM_SPRING_BOOT);
    }
}
