package dev.rckft.authservice.controllers;

import dev.rckft.authservice.controllers.request.AuthRequest;
import dev.rckft.authservice.controllers.request.RefreshRequest;
import dev.rckft.authservice.controllers.request.UserRegisterRequest;
import dev.rckft.authservice.controllers.response.AuthTokens;
import dev.rckft.authservice.security.JwtUtil;
import dev.rckft.authservice.service.UserRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserRegistrationService userRegistrationService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserDetailsService userDetailsService,
                          UserRegistrationService userRegistrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
        userRegistrationService.register(request);
        return ResponseEntity.status(CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokens> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username());
        AuthTokens tokens = jwtUtil.generateTokens(userDetails.getUsername());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokens> refreshAccessToken(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(jwtUtil.refreshAccessToken(refreshRequest.refreshToken()));
    }


}