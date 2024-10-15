package com.example.springSecurityWebWithJWT.controllers;

import com.example.springSecurityWebWithJWT.entities.User;
import com.example.springSecurityWebWithJWT.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Save user details
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

}
