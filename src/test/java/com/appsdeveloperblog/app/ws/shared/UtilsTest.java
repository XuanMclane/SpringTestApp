package com.appsdeveloperblog.app.ws.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {
    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateUserId() {
        String userId = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);
        assertNotNull(userId);
        assertTrue(userId.length() == 30);
        assertNotNull(userId2);
        assertTrue(!userId.equalsIgnoreCase(userId2));
    }

    @Test
    void hasTokenExpired() {
        String userId = utils.generateUserId(30);
        String token = utils.generateEmailVerificationToken(userId);
        boolean hasTokenExpired = Utils.hasTokenExpired(token);
        assertFalse(hasTokenExpired);
    }
}