package com.example.taskmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.entity.Tasks;
import com.example.taskmanagement.events.TaskEvent;
import com.example.taskmanagement.kafka.TaskEventProducer;
import com.example.taskmanagement.repository.TasksRepository;
import com.example.taskmanagement.service.TaskService;

@Service
public class TasksServiceImpl implements TaskService {

	@Autowired
	private TasksRepository tasksRepo;

	@Autowired
	private TaskEventProducer eventProducer;

	// ----------------------------------
	// Get All Tasks
	// ----------------------------------
	@Override
	public List<Tasks> getAllTasks() {
		return tasksRepo.findAll();
	}

	// ----------------------------------
	// Get Task By ID (Fixed Proper Logic)
	// ----------------------------------
	@Override
	public Tasks getByTaskId(Long id) {
		return tasksRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
	}

	// ----------------------------------
	// Create Task (Insert)
	// ----------------------------------
	@Override
	public Tasks saveTask(Tasks task) {
		return tasksRepo.save(task);
	}

	// ----------------------------------
	// Update Task
	// ----------------------------------
	@Override
	public Tasks updateTask(Long id, Tasks updatedTask) {
		Tasks existing = tasksRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Cannot update. Task not found with id: " + id));

		existing.setTitle(updatedTask.getTitle());
		existing.setDescription(updatedTask.getDescription());
		existing.setPriority(updatedTask.getPriority());
		existing.setStatus(updatedTask.getStatus());
		existing.setTags(updatedTask.getTags());
		existing.setDueDate(updatedTask.getDueDate());
		existing.setUpdatedAt(updatedTask.getUpdatedAt());

		return tasksRepo.save(existing);
	}

	// ----------------------------------
	// Combined Save/Update - upsert logic
	// ----------------------------------

	public Tasks createOrUpdateTask(Tasks task) {
		// If ID is null => insert
		if (task.getId() == null) {
			return tasksRepo.save(task);
		}

		// If ID exists => update
		return updateTask(task.getId(), task);
	}

	// ----------------------------------
	// Delete Task
	// ----------------------------------
	@Override
	public void deleteTask(Long taskId) {
		if (!tasksRepo.existsById(taskId)) {
			throw new RuntimeException("Delete failed. Task ID does not exist: " + taskId);
		}
		tasksRepo.deleteById(taskId);
	}

	// ----------------------------------
	// Check Exists
	// ----------------------------------
	@Override
	public boolean existsById(Long taskId) {
		return tasksRepo.existsById(taskId);
	}

	@Override
	public List<Tasks> getTasksByUserId(Long userId) {
		return tasksRepo.findByCreatedById(userId);
	}

	// kafka event

	@Override
	public Tasks assignTaskToEmployee(Long taskId, Long employeeId) {

		Tasks task = tasksRepo.findById(taskId)
				.orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

		// Update task assignment
		task.setAssignedEmployeeId(employeeId);
		task.setStatus("ASSIGNED");

		Tasks savedTask = tasksRepo.save(task);

		// Publish Kafka event AFTER DB save
		TaskEvent event = new TaskEvent(savedTask.getId(), employeeId, "TASK_ASSIGNED");

		eventProducer.publishTaskAssigned(event);

		return savedTask;
	}

}
