package com.jktechproject.documentmanagement;

import com.jktechproject.documentmanagement.controller.AuthenticationController;
import com.jktechproject.documentmanagement.dto.UserLogin;
import com.jktechproject.documentmanagement.repository.UserRepoRole;
import com.jktechproject.documentmanagement.security.JWTTokenAuthenticator;
import com.jktechproject.documentmanagement.security.JWTUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTTokenAuthenticator jwtTokenAuthenticator;

    @Mock
    private JWTUtility jwtUtility;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepoRole userRepoRole;

    @Test
    void testLoginSuccess() {
        UserLogin login = new UserLogin("testuser", "password");

        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(jwtUtility.generateJWTToken("testuser")).thenReturn("mockToken");

        ResponseEntity<?> response = authenticationController.userLogin(login);

        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testLoginFailure() {
        UserLogin login = new UserLogin("testuser", "wrongpassword");
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Bad credentials"));

        ResponseEntity<?> response = authenticationController.userLogin(login);

        Assertions.assertEquals(400, response.getStatusCodeValue());
    }
}

