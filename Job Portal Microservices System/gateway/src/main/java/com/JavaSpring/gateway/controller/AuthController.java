package com.JavaSpring.gateway.controller;

import com.JavaSpring.gateway.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    // For demo purposes, we'll accept any username/password
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password) {
        // In a real application, you would validate credentials here
        String token = jwtUtil.generateToken(username);
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Login successful");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, String> response = new HashMap<>();
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.put("valid", "false");
            response.put("message", "Invalid token format");
            return ResponseEntity.badRequest().body(response);
        }

        String token = authHeader.substring(7);
        boolean isValid = jwtUtil.validateToken(token);
        
        if (isValid) {
            String username = jwtUtil.getUsernameFromToken(token);
            response.put("valid", "true");
            response.put("username", username);
            response.put("message", "Token is valid");
            return ResponseEntity.ok(response);
        } else {
            response.put("valid", "false");
            response.put("message", "Invalid token");
            return ResponseEntity.badRequest().body(response);
        }
    }
} 