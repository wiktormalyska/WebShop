package com.wiktormalyska.backend.configuration.auth;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class TokenRestController {
    private final JWTAuthFacade jwtAuthFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<JWTResponseDto> fetchToken(@RequestBody @Valid TokenRequestDto dto){
        final JWTResponseDto response = jwtAuthFacade.authenticateAndGenerateToken(dto);
        log.info("Token generated: {}",response);
        return ResponseEntity.ok(response);
    }
}
