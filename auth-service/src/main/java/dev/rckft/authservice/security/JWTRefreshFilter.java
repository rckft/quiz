package dev.rckft.authservice.security;

import dev.rckft.authservice.exception.InvalidTokenException;
import dev.rckft.authservice.service.RevokedTokensService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class JWTRefreshFilter extends OncePerRequestFilter {

    private final RevokedTokensService revokedTokensService;
    private final JwtUtil jwtUtil;
    private final HandlerExceptionResolver exceptionResolver;

    public JWTRefreshFilter(RevokedTokensService revokedTokensService,
                            JwtUtil jwtUtil,
                            HandlerExceptionResolver exceptionResolver) {
        this.revokedTokensService = revokedTokensService;
        this.jwtUtil = jwtUtil;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String refreshToken = request.getHeader("X-Refresh-Token");
        if (isTokenInvalid(refreshToken)) {
            exceptionResolver.resolveException(request, response, null, new InvalidTokenException());
            return;
        }
        doFilter(request, response, filterChain);
    }

    private boolean isTokenInvalid(String refreshToken) {
        return refreshToken == null || jwtUtil.isTokenExpired(refreshToken) || isTokenRevoked(refreshToken);
    }

    private boolean isTokenRevoked(String refreshToken) {
        return revokedTokensService.isTokenRevoked(refreshToken);
    }
}
