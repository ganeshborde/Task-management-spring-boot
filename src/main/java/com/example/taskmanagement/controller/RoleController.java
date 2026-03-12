package com.example.taskmanagement.controller;

import com.example.taskmanagement.entity.Role;
import com.example.taskmanagement.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

	private final RoleService roleService;

	// CREATE ROLE
	@PostMapping
	public Role createRole(@RequestBody Role role) {
		return roleService.createRole(role);
	}

	// GET ALL ROLES
	@GetMapping
	public List<Role> getAllRoles() {
		return roleService.getAllRoles();
	}

	// GET ROLE BY ID
	@GetMapping("/{id}")
	public Role getRoleById(@PathVariable Long id) {
		return roleService.getRoleById(id);
	}

	// GET ROLE BY NAME
	@GetMapping("/name/{name}")
	public Role getRoleByName(@PathVariable String name) {
		return roleService.getRoleByName(name);
	}

	// DELETE ROLE
	@DeleteMapping("/{id}")
	public String deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		return "Role deleted successfully";
	}
}
