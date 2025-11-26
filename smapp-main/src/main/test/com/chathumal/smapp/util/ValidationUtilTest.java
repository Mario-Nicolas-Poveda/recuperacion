package com.chathumal.smapp.util;

import com.chathumal.smapp.entity.SuperEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {
    @Test
    public void testValidEmail() {
        assertTrue(ValidationUtil.isValidEmail("test@example.com"));
        assertTrue(ValidationUtil.isValidEmail("edu@chathumal.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(ValidationUtil.isValidEmail("plainaddress"));
        assertFalse(ValidationUtil.isValidEmail("@missingusername.com"));
        assertFalse(ValidationUtil.isValidEmail("username@domain"));
        assertFalse(ValidationUtil.isValidEmail("user@domain@domain.com"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(ValidationUtil.isValidPassword("password"));
        assertTrue(ValidationUtil.isValidPassword("1234"));
        assertTrue(ValidationUtil.isValidPassword("abcd"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(ValidationUtil.isValidPassword("123"));
        assertFalse(ValidationUtil.isValidPassword("ab"));
        assertFalse(ValidationUtil.isValidPassword(""));
    }
}
