package dev.rckft.authservice.controllers;

import dev.rckft.authservice.model.user.User;
import dev.rckft.authservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AuthControllerIntegrationTest {
    private static final String TEST_USERNAME = "TEST_USERNAME";
    private static final String TEST_PASSWORD = "TEST_PASSWORD";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldRegisterUser() {
        //given
        UserRegisterRequest request = new UserRegisterRequest(TEST_USERNAME, TEST_PASSWORD);

        //when then
        webTestClient.post()
                .uri("/api/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();

        Optional<User> user = userRepository.findByUsername(TEST_USERNAME);

        assertTrue(user.isPresent());
        assertEquals(TEST_USERNAME, user.get().getUsername());
        assertEquals(TEST_PASSWORD, user.get().getPassword());
    }

}