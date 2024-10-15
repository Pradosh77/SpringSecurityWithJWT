package com.example.springSecurityWebWithJWT.controllers;


import com.example.springSecurityWebWithJWT.entities.JwtRequest;
import com.example.springSecurityWebWithJWT.entities.JwtResponse;
import com.example.springSecurityWebWithJWT.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
            );

            // Generate the JWT token
            String token = jwtTokenUtil.generateToken(jwtRequest.getUsername());

            // Return the token in the response
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (AuthenticationException e) {
            // Return unauthorized status if authentication fails
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
