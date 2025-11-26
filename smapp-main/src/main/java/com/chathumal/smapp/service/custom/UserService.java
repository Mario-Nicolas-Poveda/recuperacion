package com.chathumal.smapp.service.custom;

import com.chathumal.smapp.dto.UserDTO;

import java.util.List;

/**
 * Service interface for user operations.
 * Follows SOLID principles similar to IDatabaseConnection.
 * 
 * Follows:
 * - SRP: Business logic only (no transaction/connection management)
 * - OCP: Open for extension with different implementations
 * - LSP: All implementations are substitutable
 */
public interface UserService {
        /**
         * Add a new user
         * 
         * @param userDTO User data
         * @return true if successful
         * @throws Exception if operation fails
         */
        boolean addUser(UserDTO userDTO) throws Exception;

        /**
         * Update an existing user
         * 
         * @param userDTO User data with ID
         * @throws Exception if operation fails
         */
        void updateUser(UserDTO userDTO) throws Exception;

        /**
         * Find user by email
         * 
         * @param email User email
         * @return User data or null if not found
         * @throws Exception if operation fails
         */
        UserDTO findByEmail(String email) throws Exception;

        /**
         * Get all users
         * 
         * @return List of all users
         * @throws Exception if operation fails
         */
        List<UserDTO> findAllUsers() throws Exception;
}
