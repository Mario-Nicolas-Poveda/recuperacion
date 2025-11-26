package com.chathumal.smapp.service.mapper;

import com.chathumal.smapp.dto.UserDTO;
import com.chathumal.smapp.entity.User;


public class UserMapperImpl implements IUserMapper {

    @Override
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setAddress(dto.getAddress());
        user.setContact(dto.getContact());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFulacs(dto.isFulacs());

        return user;
    }

    @Override
    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getContact(),
                entity.getEmail(),
                entity.getPassword(),
                entity.isFulacs());
    }
}
