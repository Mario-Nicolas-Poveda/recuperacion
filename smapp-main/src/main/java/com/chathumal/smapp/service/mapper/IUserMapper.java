package com.chathumal.smapp.service.mapper;

import com.chathumal.smapp.dto.UserDTO;
import com.chathumal.smapp.entity.User;

/**
 * Interface for mapping between User entities and DTOs.
 * Follows SOLID principles similar to IDatabaseConnection.
 * 
 * Follows:
 * - SRP: Single responsibility of conversion logic
 * - OCP: Open for extension (can create enhanced mappers)
 * - LSP: All implementations are substitutable
 */
public interface IUserMapper {
    /**
     * Convert DTO to Entity
     * 
     * @param dto User data transfer object
     * @return User entity
     */
    User toEntity(UserDTO dto);

    /**
     * Convert Entity to DTO
     * 
     * @param entity User entity
     * @return User data transfer object
     */
    UserDTO toDTO(User entity);
}
