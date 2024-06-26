package com.wiktormalyska.backend.configuration.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.IOException;
import java.util.Collections;

@Component
@AllArgsConstructor
public class JWTAuthTokenFilter extends OncePerRequestFilter {

    private final JWTConfigProperties jwtConfigProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null){
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getUsernamePasswordAuthenticationToken(authorization);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String authorization) {
        String secretKey = jwtConfigProperties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT jwt = verifier.verify(authorization.substring(7));
        String role = jwt.getClaim("role").asString();
        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role)));

    }
}
