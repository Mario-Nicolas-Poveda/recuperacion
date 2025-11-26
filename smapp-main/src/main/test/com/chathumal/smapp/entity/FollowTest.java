package com.chathumal.smapp.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FollowTest {

    private Follow follow;
    private User user;
    private User follower;

    @BeforeEach
    void setUp() {
        user = new User(1, "John Doe", "123 Main Street", "1234567890", "john@example.com", "password123", true);
        follower = new User(2, "Jane Smith", "456 Elm Street", "0987654321", "jane@example.com", "password456", true);

        follow = new Follow(10001, user, follower);
    }

    @Test
    void testGetters() {
        assertEquals(10001, follow.getFid());
        assertEquals(user, follow.getUser());
        assertEquals(follower, follow.getFlwusr());
    }

    @Test
    void testSetters() {
        User newUser = new User(3, "Alex Brown", "789 Oak Avenue", "5678901234", "alex@example.com", "password789", true);
        User newFollower = new User(4, "Emily White", "321 Pine Lane", "6543210987", "emily@example.com", "password321", true);

        follow.setFid(10002);
        follow.setUser(newUser);
        follow.setFlwusr(newFollower);

        assertEquals(10002, follow.getFid());
        assertEquals(newUser, follow.getUser());
        assertEquals(newFollower, follow.getFlwusr());
    }

    @Test
    void testToString() {
        String expectedString = "Follow{fid=10001, user=" + user + ", flwusr=" + follower + "}";
        assertEquals(expectedString, follow.toString());
    }
}

