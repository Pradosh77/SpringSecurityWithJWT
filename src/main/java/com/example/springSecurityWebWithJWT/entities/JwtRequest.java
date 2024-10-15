package com.example.springSecurityWebWithJWT.entities;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtRequest {

    private String username;
    private String password;
}
