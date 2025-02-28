package dev.rckft.authservice.controllers;

import dev.rckft.authservice.controllers.request.AuthRequest;
import dev.rckft.authservice.controllers.request.RefreshRequest;
import dev.rckft.authservice.controllers.request.UserRegisterRequest;
import dev.rckft.authservice.controllers.response.AuthTokens;
import dev.rckft.authservice.model.user.User;
import dev.rckft.authservice.repository.UserRepository;
import dev.rckft.authservice.security.JwtTestUtil;
import dev.rckft.authservice.security.JwtUtil;
import dev.rckft.authservice.service.UserRegistrationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;
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

    @Autowired
    private JwtTestUtil jwtTestUtil;

    @BeforeEach
    void setUp() {
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
    void shouldNotRegisterUser_whenUserWithGivenUsernameExists() {
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
    void shouldReturnJwtAccessAndRefreshTokens_whenLoggedIn() {
        //given
        AuthRequest authRequest = new AuthRequest(EXISTING_TEST_USERNAME, EXISTING_TEST_PASSWORD);

        //when
        AuthTokens responseBody = webTestClient.post()
                .uri("/api/auth/login")
                .bodyValue(authRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthTokens.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);

        String accessToken = responseBody.accessToken();
        String refreshToken = responseBody.refreshToken();
        assertNotNull(accessToken);
        assertNotNull(refreshToken);
        assertEquals(EXISTING_TEST_USERNAME, jwtUtil.extractUsername(accessToken));
        assertEquals(EXISTING_TEST_USERNAME, jwtUtil.extractUsername(refreshToken));

        assertJtiClaimSameForTokens(accessToken, refreshToken);
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

    @Test
    void shouldCreateNewAccessToken_whenRefreshingAccessToken() {
        //given
        String refreshToken = jwtUtil.generateTokens(EXISTING_TEST_USERNAME).refreshToken();
        String expiredAccessToken = jwtUtil.generateTokens(EXISTING_TEST_USERNAME).accessToken();
        RefreshRequest refreshRequest = new RefreshRequest(refreshToken);

        //when
        AuthTokens responseBody = webTestClient.post()
                .uri("api/auth/refresh")
                .bodyValue(refreshRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthTokens.class)
                .returnResult()
                .getResponseBody();


        assertNotNull(responseBody);

        String responseAccessToken = responseBody.accessToken();
        String responseRefreshToken = responseBody.refreshToken();

        assertNotNull(responseAccessToken);
        assertNotNull(responseRefreshToken);
        assertEquals(EXISTING_TEST_USERNAME, jwtUtil.extractUsername(responseAccessToken));
        assertEquals(EXISTING_TEST_USERNAME, jwtUtil.extractUsername(responseRefreshToken));
        assertEquals(refreshToken, responseRefreshToken);
        assertNotEquals(expiredAccessToken, responseAccessToken);

        assertJtiClaimSameForTokens(responseAccessToken, responseRefreshToken);
        assertResponseAccessTokenExpiryDate(responseAccessToken, responseRefreshToken);
    }

    private void assertJtiClaimSameForTokens(String accessToken, String refreshToken) {
        String accessTokenJit = jwtTestUtil.getClaims(accessToken).get("jit", String.class);
        String refreshTokenJit = jwtTestUtil.getClaims(refreshToken).get("jit", String.class);

        assertEquals(refreshTokenJit, accessTokenJit);
    }

    private void assertResponseAccessTokenExpiryDate(String expiredAccessToken, String responseAccessToken) {
        Date expiredAccessTokenExpiryDate = jwtTestUtil.getClaims(expiredAccessToken).get("exp", Date.class);
        Date responseAccessTokenExpiryDate = jwtTestUtil.getClaims(responseAccessToken).get("exp", Date.class);

        assertTrue(responseAccessTokenExpiryDate.after(expiredAccessTokenExpiryDate));
    }


}