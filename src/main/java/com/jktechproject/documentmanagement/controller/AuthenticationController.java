package com.jktechproject.documentmanagement.controller;

import com.jktechproject.documentmanagement.dto.UserAuthentication;
import com.jktechproject.documentmanagement.dto.UserLogin;
import com.jktechproject.documentmanagement.dto.UserRegisteration;
import com.jktechproject.documentmanagement.entity.User;
import com.jktechproject.documentmanagement.entity.UserRole;
import com.jktechproject.documentmanagement.security.JWTTokenAuthenticator;
import com.jktechproject.documentmanagement.security.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.jktechproject.documentmanagement.repository.UserRepoRole;

import java.util.Set;

@RestController
@RequestMapping("/jktech/docman/auth")
public class AuthenticationController {

    @Autowired
    private UserRepoRole userRepoRole;

    private final AuthenticationManager authenticationManager;
    private final JWTTokenAuthenticator jwtTokenAuthenticator;
    private final JWTUtility jwtUtility;
    private final UserDetailsService userDetailsService;
private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager, JWTTokenAuthenticator jwtTokenAuthenticator, JWTUtility jwtUtility, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenAuthenticator = jwtTokenAuthenticator;
        this.jwtUtility = jwtUtility;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/status")
    public ResponseEntity<String> getServiceStatus(){

        return ResponseEntity.ok("Hello. You are authenticated user.");
    }



    @PostMapping("/register")
    public ResponseEntity<?> userRegisteration(@RequestBody UserRegisteration authUser){
        if (!userRepoRole.existsByUsername(authUser.getUserName())) {
            User client = new User();
            client.setUsername(authUser.getUserName());
            client.setPassword(passwordEncoder.encode(authUser.getPassword()));
            client.setName(authUser.getName());
            client.setRoles(Set.of(UserRole.VIEWER));
            userRepoRole.save(client);
            return ResponseEntity.ok(new UserAuthentication("User created successfully", "00"));
        } else {
            return ResponseEntity.badRequest().body("User already exists.");
        }
    }

@PostMapping("/login")
    public ResponseEntity<?>userLogin(@RequestBody UserLogin userLog) {

    try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLog.getUsserName(),
                        userLog.getPassword()
                )
        );


        String token = jwtUtility.generateJWTToken(userLog.getUsserName());

        return ResponseEntity.ok(new UserAuthentication("Login Successful", token));
    } catch (Exception ex) {
        return ResponseEntity.badRequest().body("Invalid credentials");
    }
}
}
