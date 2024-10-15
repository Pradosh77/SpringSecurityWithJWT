package com.example.springSecurityWebWithJWT.services;

import com.example.springSecurityWebWithJWT.entities.User;
import com.example.springSecurityWebWithJWT.models.AuthUser;
import com.example.springSecurityWebWithJWT.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User not Found");
        }

        return new AuthUser(user);
    }
}
