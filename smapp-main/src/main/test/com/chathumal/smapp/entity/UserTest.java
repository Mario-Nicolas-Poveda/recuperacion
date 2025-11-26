package com.chathumal.smapp.entity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();

        assertNull(user.getName());
        assertNull(user.getAddress());
        assertNull(user.getContact());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertFalse(user.isFulacs());
    }

    @Test
    void testParameterizedConstructor() {
        User user = new User(1, "John Doe", "123 Street", "1234567890", "john@example.com", "securePassword", true);

        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("123 Street", user.getAddress());
        assertEquals("1234567890", user.getContact());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("securePassword", user.getPassword());
        assertTrue(user.isFulacs());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setId(2);
        user.setName("Jane Doe");
        user.setAddress("456 Avenue");
        user.setContact("0987654321");
        user.setEmail("jane@example.com");
        user.setPassword("anotherSecurePassword");
        user.setFulacs(false);

        assertEquals(2, user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("456 Avenue", user.getAddress());
        assertEquals("0987654321", user.getContact());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("anotherSecurePassword", user.getPassword());
        assertFalse(user.isFulacs());
    }

    @Test
    void testUniqueConstraints() {
        User user1 = new User(1, "John Doe", "123 Street", "1234567890", "johns@example.com", "securePassword", true);
        User user2 = new User(2, "Jane Doe", "456 Avenue", "1234567891", "john@example.com", "anotherPassword", false);

        assertNotEquals(user1.getContact(), user2.getContact(), "Contact numbers must be unique");
        assertNotEquals(user1.getEmail(), user2.getEmail(), "Emails must be unique");
    }
}
