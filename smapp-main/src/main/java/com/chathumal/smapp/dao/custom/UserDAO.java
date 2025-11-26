package com.chathumal.smapp.dao.custom;

import com.chathumal.smapp.dao.CrudDAO;

import com.chathumal.smapp.entity.User;
import com.chathumal.smapp.exception.NotFoundException;

public interface UserDAO extends CrudDAO<User, String> {
    User findByEmail(String email) throws NotFoundException;

}
