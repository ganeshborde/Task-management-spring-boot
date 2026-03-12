package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    void deleteUser(Long id);
}
