package dev.rckft.authservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<?> register() throws Exception {
        System.out.println("hello login");
        return ResponseEntity.ok().build();
    }
}

