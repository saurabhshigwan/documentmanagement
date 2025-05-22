
package com.jktechproject.documentmanagement;

import com.jktechproject.documentmanagement.service.RedisTokenSErvice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisTokenSErviceTest {

    @Autowired
    private RedisTokenSErvice redisTokenSErvice;

    @Test
    void testTokenStorage() {
        String token = "sampleToken123";
        redisTokenSErvice.storeToken(token, 1000);

        Assertions.assertTrue(redisTokenSErvice.isTokenValid(token));

        redisTokenSErvice.deleteToken(token);

        Assertions.assertFalse(redisTokenSErvice.isTokenValid(token));
    }
}

