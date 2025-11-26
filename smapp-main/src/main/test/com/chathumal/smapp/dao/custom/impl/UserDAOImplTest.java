package com.chathumal.smapp.dao.custom.impl;

import com.chathumal.smapp.configuration.FactoryConfiguration;
import com.chathumal.smapp.entity.User;
import com.chathumal.smapp.exception.NotFoundException;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDAOImplTest {

    private Session session;
    private UserDAOImpl userDAO;

    @BeforeEach
    public void setUp() {
        session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        userDAO = new UserDAOImpl();
        userDAO.setSession(session);
    }

    @AfterEach
    public void tearDown() {
        if (session != null) {
            session.getTransaction().commit();
            session.close();
        }
    }

    @Test
    void findByEmail() throws NotFoundException {
        String emailToFind = "edu@chathumal.com";

        User userToSave = new User(0, "Admin", "Padukka", "0765042201", emailToFind, "test@123", true);
        session.save(userToSave);
        session.getTransaction().commit();
        session.beginTransaction();

        User user = userDAO.findByEmail(emailToFind);
        assertNotNull(user);
        assertEquals(userToSave.getId(), user.getId());
//        assertEquals(userToSave.isFulacs(), user.isFulacs());
        assertEquals(userToSave.getAddress(), user.getAddress());
        assertEquals(userToSave.getContact(), user.getContact());
        assertEquals(userToSave.getEmail(), user.getEmail());
        assertEquals(userToSave.getName(), user.getName());
        assertEquals(userToSave.getPassword(), user.getPassword());
        System.out.println("user = " + user);
    }
}
