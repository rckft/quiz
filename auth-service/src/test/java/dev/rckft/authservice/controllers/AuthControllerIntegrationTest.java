package dev.rckft.authservice.controllers;

import dev.rckft.authservice.model.user.User;
import dev.rckft.authservice.repository.UserRepository;
import dev.rckft.authservice.security.JwtUtil;
import dev.rckft.authservice.service.UserRegistrationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AuthControllerIntegrationTest {
    private static final String EXISTING_TEST_USERNAME = "EXISTING_TEST_USERNAME";
    private static final String EXISTING_TEST_PASSWORD = "EXISTING_TEST_PASSWORD";

    private static final String NEW_TEST_USERNAME = "NEW_TEST_USERNAME";
    private static final String NEW_TEST_PASSWORD = "NEW_TEST_PASSWORD";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest(EXISTING_TEST_USERNAME, EXISTING_TEST_PASSWORD);
        userRegistrationService.register(request);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterUser() {
        //given
        UserRegisterRequest request = new UserRegisterRequest(NEW_TEST_USERNAME, NEW_TEST_PASSWORD);

        //when then
        webTestClient.post()
                .uri("/api/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();

        Optional<User> user = userRepository.findByUsername(NEW_TEST_USERNAME);

        assertTrue(user.isPresent());
        assertEquals(NEW_TEST_USERNAME, user.get().getUsername());
        assertTrue(passwordEncoder.matches(NEW_TEST_PASSWORD, user.get().getPassword()));
    }

    @Test
    void shouldNotRegisterUser_WhenUserWithGivenUsernameExists() {
        //given
        UserRegisterRequest request = new UserRegisterRequest(EXISTING_TEST_USERNAME, EXISTING_TEST_PASSWORD);

        //when then
        webTestClient.post()
                .uri("/api/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();

        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void shouldReturnJwt_whenLoggedIn() {
        //given
        AuthRequest authRequest = new AuthRequest(EXISTING_TEST_USERNAME, EXISTING_TEST_PASSWORD);

        //when
        AuthResponse responseBody = webTestClient.post()
                .uri("/api/auth/login")
                .bodyValue(authRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponse.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertNotNull(responseBody.jwt());
        assertEquals(EXISTING_TEST_USERNAME, jwtUtil.extractUsername(responseBody.jwt()));
    }

    @Test
    void shouldNotLoginUser_whenPasswordIsWrong() {
        //given
        AuthRequest authRequest = new AuthRequest(EXISTING_TEST_USERNAME, "BAD_PASSWORD");

        //when then
        webTestClient.post()
                .uri("/api/auth/login")
                .bodyValue(authRequest)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void shouldNotLoginUser_whenUserNotExist() {
        //given
        AuthRequest authRequest = new AuthRequest(NEW_TEST_USERNAME, NEW_TEST_PASSWORD);

        //when then
        webTestClient.post()
                .uri("/api/auth/login")
                .bodyValue(authRequest)
                .exchange()
                .expectStatus().isForbidden();
    }

}