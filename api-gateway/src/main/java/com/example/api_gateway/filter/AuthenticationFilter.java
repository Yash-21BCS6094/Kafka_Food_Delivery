package com.example.api_gateway.filter;
import com.example.api_gateway.exception.InvalidAccessException;
import com.example.api_gateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * AuthenticationFilter is a custom GatewayFilter that ensures secure access to API routes.
 * It checks if the request is targeting a secured route using RoutesValidator.
 * If the route is secured, it validates the presence and format of the JWT token from the Authorization header.
 * The token is verified using JwtUtil, and the user's role is extracted and added to the request headers.
 * If authentication fails, an InvalidAccessException is thrown.
 * This filter helps enforce security by ensuring only authenticated and authorized users can access protected resources.
 */

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RoutesValidator validator;
    private final JwtUtil jwtUtil;
    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    public AuthenticationFilter(RoutesValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            log.info("Incoming request to: {}", path);

            if (validator.isSecured.test(exchange.getRequest())) {
                log.info("Secured route detected: {}", path);

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.warn("Missing authorization header for request: {}", path);
                    throw new InvalidAccessException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                log.info("Authorization header found");

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                } else {
                    log.warn("Invalid token format in request: {}", path);
                    throw new InvalidAccessException("Invalid authorization token");
                }

                try {
                    jwtUtil.validateToken(authHeader);
                    log.info("JWT validation successful for request: {}", path);

                    // Extract user role from JWT claims
                    String role = jwtUtil.extractRoleFromToken(authHeader);
                    String username = jwtUtil.extractUsernameFromToken(authHeader);
                    log.info("User role extracted: {}", role);

                    // Modify the request to include the user role in the headers
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("X-User-Role", role)
                            .header("X-User-Username", username)
                            .build();

                    return chain.filter(exchange.mutate().request(modifiedRequest).build());

                } catch (Exception e) {
                    log.error("Unauthorized access detected: {}", e.getMessage());
                    throw new InvalidAccessException("Unauthorized access to application");
                }
            } else {
                log.info("Public route accessed: {}", path);
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
