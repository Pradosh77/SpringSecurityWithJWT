package com.example.springSecurityWebWithJWT.entities;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String jwtToken;
}
