package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role);

    Role getRoleById(Long id);

    List<Role> getAllRoles();

    Role getRoleByName(String name);

    void deleteRole(Long id);
}
