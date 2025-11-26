package com.chathumal.smapp.dao.custom.impl;

import com.chathumal.smapp.dao.CrudDAOImpl;
import com.chathumal.smapp.dao.custom.UserDAO;

import com.chathumal.smapp.entity.User;
import com.chathumal.smapp.exception.NotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends CrudDAOImpl<User, String> implements UserDAO {

    @Override
    public void add(User user) throws Exception {
        String sql = "INSERT INTO users (name, address, contact, email, password, fulacs) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getAddress());
            pstmt.setString(3, user.getContact());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.setBoolean(6, user.isFulacs());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(String id) throws Exception {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws Exception {
        String sql = "UPDATE users SET name = ?, address = ?, contact = ?, email = ?, password = ?, fulacs = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getAddress());
            pstmt.setString(3, user.getContact());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.setBoolean(6, user.isFulacs());
            pstmt.setInt(7, user.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, address, contact, email, password, fulacs FROM users";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setContact(rs.getString("contact"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFulacs(rs.getBoolean("fulacs"));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public User search(String id) throws Exception {
        String sql = "SELECT id, name, address, contact, email, password, fulacs FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setAddress(rs.getString("address"));
                    user.setContact(rs.getString("contact"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setFulacs(rs.getBoolean("fulacs"));
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        System.out.println("UserDAOImpl: Attempting to find user by email: " + email);
        String sql = "SELECT id, name, address, contact, email, password, fulacs FROM users WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setAddress(rs.getString("address"));
                    user.setContact(rs.getString("contact"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setFulacs(rs.getBoolean("fulacs"));
                    System.out.println("UserDAOImpl: User found: " + user.getName());
                    return user;
                } else {
                    System.out.println("UserDAOImpl: No user found for email: " + email);
                    throw new NotFoundException("User not found");
                }
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("UserDAOImpl: Error finding user: " + e.getMessage());
            e.printStackTrace();
            throw new NotFoundException("User not found");
        }
    }

}
