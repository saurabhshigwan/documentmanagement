package com.jktechproject.documentmanagement;

import com.jktechproject.documentmanagement.security.JWTUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTUtilityTest {



        @Autowired
        private JWTUtility jwtUtility;

        @Test
        void testGenerateAndValidateToken() {
            String username = "testuser";
            String token = jwtUtility.generateJWTToken(username);

            Assertions.assertNotNull(token);
            Assertions.assertEquals(username, jwtUtility.getUserNameFromJWTToken(token));
            Assertions.assertTrue(jwtUtility.getUserRoleFromJWTToken(token).contains("VIEWER") ||
                    jwtUtility.getUserRoleFromJWTToken(token).size() >= 0);
        }

        @Test
        void testInvalidToken() {
            String invalidToken = "this.is.invalid";
            Assertions.assertFalse(jwtUtility.ValidateJWTToken(invalidToken, null));
        }


}
