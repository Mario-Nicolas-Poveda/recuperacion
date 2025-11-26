package com.chathumal.smapp.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionTest  {

    @Test
    void testSingletonInstance() {
        UserSession instance1 = UserSession.getInstance();
        UserSession instance2 = UserSession.getInstance();

        assertSame(instance1, instance2, "Instances are not the same!");
    }

    @Test
    void testSetAndGetUsername() {
        UserSession userSession = UserSession.getInstance();
        String testEmail = "testUser@gmailcom";
        userSession.setEmail(testEmail);

        assertEquals(testEmail, userSession.getEmail(), "The retrieved email is incorrect!");
    }

    @Test
    void testDefaultUsername() {
        UserSession userSession = UserSession.getInstance();

        assertNull(userSession.getEmail(), "Default email is not null!");
    }}
