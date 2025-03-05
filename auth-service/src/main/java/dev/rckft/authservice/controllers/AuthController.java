package dev.rckft.authservice.controllers;

import dev.rckft.authservice.controllers.request.AuthRequest;
import dev.rckft.authservice.controllers.request.LogoutRequest;
import dev.rckft.authservice.controllers.request.UserRegisterRequest;
import dev.rckft.authservice.controllers.response.AuthTokens;
import dev.rckft.authservice.exception.InvalidTokenException;
import dev.rckft.authservice.security.JwtUtil;
import dev.rckft.authservice.service.RevokedTokensService;
import dev.rckft.authservice.service.UserRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserRegistrationService userRegistrationService;
    private final RevokedTokensService revokedTokensService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserDetailsService userDetailsService,
                          UserRegistrationService userRegistrationService,
                          RevokedTokensService revokedTokensService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
        this.revokedTokensService = revokedTokensService;
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
    public ResponseEntity<AuthTokens> refreshAccessToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return ResponseEntity.ok(jwtUtil.refreshAccessToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> blockRefreshToken(@RequestBody LogoutRequest logoutRequest) {
        String refreshToken = logoutRequest.refreshToken();
        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new InvalidTokenException();
        }

        revokedTokensService.revokeToken(refreshToken);
        return ResponseEntity.ok().build();
    }
}