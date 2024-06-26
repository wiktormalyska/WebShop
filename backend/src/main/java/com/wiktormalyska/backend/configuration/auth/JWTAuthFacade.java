package com.wiktormalyska.backend.configuration.auth;


import com.auth0.jwt.JWT;
import com.wiktormalyska.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Log4j2
@Component
@AllArgsConstructor
public class JWTAuthFacade {

    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JWTConfigProperties jwtConfigProperties;

    public JWTResponseDto authenticateAndGenerateToken(TokenRequestDto tokenRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        tokenRequestDto.getUsername(),
                        tokenRequestDto.getPassword()
                )
        );
        final User user = (User) authenticate.getPrincipal();
        String token = createToken(user);
        String username = user.getUsername();
        return JWTResponseDto.builder()
                .token(token)
                .username(username)
                .build();
    }

    private String createToken(final User user) {
        String secretKey = jwtConfigProperties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now(clock);
        Instant expirationTime = now.plus(Duration.ofHours(jwtConfigProperties.expirationTime()));
        String issuer = "wiktormalyska WebShop Backend";

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expirationTime)
                .withIssuer(issuer)
                .withClaim("role", user.getAuthorities().iterator().next().getAuthority())
                .sign(algorithm);
    }

}
