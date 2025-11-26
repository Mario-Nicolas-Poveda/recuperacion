package com.chathumal.smapp.service.custom.impl;

import com.chathumal.smapp.dao.custom.UserDAO;
import com.chathumal.smapp.dto.UserDTO;
import com.chathumal.smapp.entity.User;
import com.chathumal.smapp.exception.DuplicateEntryException;
import com.chathumal.smapp.exception.NotFoundException;
import com.chathumal.smapp.service.custom.UserService;
import com.chathumal.smapp.service.mapper.IUserMapper;
import com.chathumal.smapp.service.transaction.ITransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of UserService with SOLID principles.
 * Modeled after PostgresDatabaseConnection architecture.
 * 
 * Follows:
 * - SRP: Business logic ONLY (no transaction/connection management)
 * - OCP: Can be extended or wrapped (e.g., LoggingUserService)
 * - LSP: Perfectly substitutable with any UserService
 * 
 * Dependencies injected via constructor (like PostgresDatabaseConnection
 * receives DatabaseConfig)
 */
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final ITransactionManager transactionManager;
    private final IUserMapper mapper;

    /**
     * Constructor with dependency injection (following configDb pattern)
     * 
     * @param userDAO            Data access object for users
     * @param transactionManager Transaction management
     * @param mapper             DTO ↔ Entity mapper
     */
    public UserServiceImpl(UserDAO userDAO, ITransactionManager transactionManager, IUserMapper mapper) {
        this.userDAO = userDAO;
        this.transactionManager = transactionManager;
        this.mapper = mapper;
    }

    @Override
    public boolean addUser(UserDTO userDTO) throws DuplicateEntryException, Exception {
        try {
            return transactionManager.executeInTransaction(connection -> {
                userDAO.setConnection(connection);
                User user = mapper.toEntity(userDTO);
                userDAO.add(user);
                return true;
            });
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DuplicateEntryException("Please check email and mobile number");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DuplicateEntryException(e.getMessage());
        }
    }

    @Override
    public void updateUser(UserDTO userDTO) throws Exception {
        try {
            transactionManager.executeInTransaction(connection -> {
                userDAO.setConnection(connection);
                User user = mapper.toEntity(userDTO);
                userDAO.update(user);
                return null;
            });
        } catch (Throwable t) {
            t.printStackTrace();
            throw new Exception("Failed to update user", t);
        }
    }

    @Override
    public UserDTO findByEmail(String email) throws NotFoundException {
        try {
            return transactionManager.executeInTransaction(connection -> {
                userDAO.setConnection(connection);
                User user = userDAO.findByEmail(email);
                return mapper.toDTO(user);
            });
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public List<UserDTO> findAllUsers() throws Exception {
        try {
            return transactionManager.executeInTransaction(connection -> {
                userDAO.setConnection(connection);
                List<User> users = userDAO.getAll();

                // Convert entities to DTOs
                List<UserDTO> userDTOs = new ArrayList<>();
                for (User user : users) {
                    userDTOs.add(mapper.toDTO(user));
                }
                return userDTOs;
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve users", e);
        }
    }
}
