package com.example.taskmanagement.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.entity.Tasks;
import com.example.taskmanagement.service.impl.TasksServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/tasks")
public class TasksController {

	@Autowired
	private TasksServiceImpl service;

	// --------------------------------------------------------
	// Get All Tasks
	// --------------------------------------------------------
	@GetMapping
	public List<Tasks> getAllTasks() {
		return service.getAllTasks();
	}

	// --------------------------------------------------------
	// Get Task By ID
	// --------------------------------------------------------
	@GetMapping("/{taskId}")
	public ResponseEntity<Tasks> getTaskById(@PathVariable Long taskId) {
		try {
			Tasks task = service.getByTaskId(taskId);
			return ResponseEntity.ok(task);
		} catch (RuntimeException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	// --------------------------------------------------------
	// Create New Task
	// --------------------------------------------------------
	@PostMapping
	public ResponseEntity<Tasks> createTask(@RequestBody Tasks task) {
		task.setCreatedAt(LocalDateTime.now());
		task.setUpdatedAt(LocalDateTime.now());
		Tasks savedTask = service.saveTask(task);
		return ResponseEntity.ok(savedTask);
	}

	// --------------------------------------------------------
	// Update Existing Task
	// --------------------------------------------------------
	@PutMapping("/{taskId}")
	public ResponseEntity<Tasks> updateTask(@PathVariable Long taskId, @RequestBody Tasks task) {
		try {
			Tasks updated = service.updateTask(taskId, task);
			return ResponseEntity.ok(updated);
		} catch (RuntimeException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	// --------------------------------------------------------
	// Delete Task
	// --------------------------------------------------------
	@DeleteMapping("/{taskId}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
		if (!service.existsById(taskId)) {
			return ResponseEntity.notFound().build();
		}
		service.deleteTask(taskId);
		return ResponseEntity.noContent().build();
	}

	// --------------------------------------------------------
	// Get Tasks by User ID (created_by field)
	// --------------------------------------------------------
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Tasks>> getTasksByUserId(@PathVariable Long userId) {
		List<Tasks> tasks = service.getTasksByUserId(userId);
		if (tasks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(tasks);
	}

	// kafka event
	@PutMapping("/{taskId}/assign/{employeeId}")
	public ResponseEntity<Tasks> assignTask(@PathVariable Long taskId, @PathVariable Long employeeId) {

		return ResponseEntity.ok(service.assignTaskToEmployee(taskId, employeeId));
	}

}
