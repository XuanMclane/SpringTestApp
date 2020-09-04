package com.appsdeveloperblog.app.ws.io.repository;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void TestGetVerifiedUsers() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUserWithConfirmedEmailAddress(pageableRequest);
        assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        assertTrue(userEntities.size() >= 1);
    }

    @Test
    void findUserByFirstName() {
        String firstName = "John";
        List<UserEntity> userEntities = userRepository.findUserByFirstName(firstName);
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 4);

        UserEntity user = userEntities.get(0);
        assertEquals(user.getFirstName(), firstName);
    }

    @Test
    void findUserByLastName() {
        String lastName = "Smith";
        List<UserEntity> userEntities = userRepository.findUserByLastName(lastName);
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 4);

        UserEntity user = userEntities.get(0);
        assertEquals(user.getLastName(), lastName);
    }

    @Test
    void findUserByKeyword() {
        String keyword = "mith";
        List<UserEntity> userEntities = userRepository.findUserByKeyword(keyword);
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 4);

        UserEntity user = userEntities.get(0);
        assertTrue(user.getLastName().contains(keyword) || user.getFirstName().contains(keyword));
    }

    @Test
    void findUserFirstNameAndLastNameByKeyword() {
        String keyword = "mith";
        List<Object[]> userEntities = userRepository.findUserFistNameAndLastNameByKeyword(keyword);
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 4);

        Object[] user = userEntities.get(0);
        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("First Name is "+ userFirstName);
        System.out.println("Last Name is " + userLastName);
    }

    @Test
    void testUpdateUserEmailVefificationStatus() {
        userRepository.updateUserEmailVerification(true, "fs0i3PIdgnv0mnx4wq0qyrlk4ah0pl");
        UserEntity user = userRepository.findByUserId("fs0i3PIdgnv0mnx4wq0qyrlk4ah0pl");
        assertEquals(user.getEmailVerificationStatus(), true);
    }

    @Test
    void testFindUserEntityByUserId() {
        String userId = "fs0i3PIdgnv0mnx4wq0qyrlk4ah0pl";
        UserEntity user = userRepository.findUserEntityByUserId(userId);
        assertNotNull(user);
        assertEquals(user.getUserId(), userId);
    }

    @Test
    void getUserFullNameByUserId() {
        String userId = "fs0i3PIdgnv0mnx4wq0qyrlk4ah0pl";
        List<Object[]> users = userRepository.getuserEntityFullNameById(userId);
        Object[] user = users.get(0);
        String firstName = String.valueOf(user[0]);
        String lastName = String.valueOf(user[0]);
        assertNotNull(firstName);
        assertNotNull(lastName);
    }

    @Test
    void updateUserEntityEmailVerification() {
        String userId = "fs0i3PIdgnv0mnx4wq0qyrlk4ah0pl";
        userRepository.updateUserEntityEmailVerificationStatus(false, userId);
        UserEntity user = userRepository.findUserEntityByUserId(userId);
        assertNotNull(user);
        assertEquals(user.getUserId(), userId);
        assertEquals(user.getEmailVerificationStatus(), false);
    }
}