package dev.rckft.authservice.controllers;

import dev.rckft.authservice.controllers.request.AuthRequest;
import dev.rckft.authservice.controllers.request.UserRegisterRequest;
import dev.rckft.authservice.controllers.response.AuthTokens;
import dev.rckft.authservice.security.JwtUtil;
import dev.rckft.authservice.service.RevokedTokensService;
import dev.rckft.authservice.service.UserRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRegistrationService userRegistrationService;
    private final RevokedTokensService revokedTokensService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserRegistrationService userRegistrationService,
                          RevokedTokensService revokedTokensService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRegistrationService = userRegistrationService;
        this.revokedTokensService = revokedTokensService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterRequest request) {
        userRegistrationService.register(request);
        return ResponseEntity.status(CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokens> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws BadCredentialsException {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        AuthTokens tokens = jwtUtil.generateTokens(authenticate.getName());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokens> refreshAccessToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return ResponseEntity.ok(jwtUtil.refreshAccessToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> blockRefreshToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        revokedTokensService.revokeToken(refreshToken);
        return ResponseEntity.ok().build();
    }
}