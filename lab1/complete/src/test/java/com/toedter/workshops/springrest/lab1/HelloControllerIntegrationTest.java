package com.toedter.workshops.springrest.lab1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class HelloControllerIntegrationTest {

    @Autowired
    private RestTestClient restClient;

    @Test
    void shouldGetGreeting() {
        String responseBody = restClient
                .get()
                .uri("/")
                .exchange()
                .returnResult(String.class)
                .getResponseBody();

        assertThat(responseBody).isEqualTo(HelloController.LAB1_GREETINGS_FROM_SPRING_BOOT);
    }
}
