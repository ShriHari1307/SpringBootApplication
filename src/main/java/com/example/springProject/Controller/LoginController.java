package com.example.springProject.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String role, HttpServletRequest request) {
        request.getSession().setAttribute("role", role);
        System.out.println("Role set in session: " + role);
        return ResponseEntity.ok("Logged in with role: " + role);
    }
}
