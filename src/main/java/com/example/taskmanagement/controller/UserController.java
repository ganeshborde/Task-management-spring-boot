package com.example.taskmanagement.controller;

import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// CREATE USER
	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	// GET ALL USERS (dashboard)
	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	// GET USER BY ID
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	// GET USER BY EMAIL
	@GetMapping("/email/{email}")
	public User getUserByEmail(@PathVariable String email) {
		return userService.getUserByEmail(email);
	}

	// DELETE USER
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "User deleted successfully";
	}
}
