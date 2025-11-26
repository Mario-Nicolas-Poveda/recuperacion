package com.chathumal.smapp.service.custom.impl;

import com.chathumal.smapp.configuration.FactoryConfiguration;
import com.chathumal.smapp.dao.custom.UserDAO;
import static org.junit.jupiter.api.Assertions.*;

import com.chathumal.smapp.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.hibernate.Session;
import org.hibernate.Transaction;

import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private FactoryConfiguration factoryConfiguration;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(factoryConfiguration.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    void testAddUser_Success() throws Exception {

        doNothing().when(userDAO).add(any(User.class));
        doNothing().when(transaction).commit();

        userService.addUser("John Doe", "123 Street", "1234567893", "johwn@example.com", "securePassword", true);

        verify(session).beginTransaction();
        verify(userDAO).add(any(User.class));
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void testAddUser_Exception_Rollback() throws Exception {
        doThrow(new RuntimeException("Database error")).when(userDAO).add(any(User.class));
        doNothing().when(transaction).rollback();

        try {
            userService.addUser("John Doe", "123 Street", "1234567890", "john@example.com", "securePassword",true);
        } catch (Exception ignored) {
        }

        verify(transaction).rollback();
        verify(session).close();
    }

    @Test
    void testLoginCheck_Success() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("john@example.com");
        mockUser.setPassword("securePassword");

        when(userDAO.findByEmail("john@example.com")).thenReturn(mockUser);

        User result = userService.findByEmail("john@example.com");

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        verify(session).beginTransaction();
        verify(session).close();
    }

    @Test
    void testLoginCheck_Exception_Rollback() throws Exception {
        when(userDAO.findByEmail("john@example.com")).thenThrow(new RuntimeException("Database error"));
        doNothing().when(transaction).rollback();

        User result = userService.findByEmail("john@example.com");

        assertNull(result);
        verify(transaction).rollback();
        verify(session).close();
    }
}

