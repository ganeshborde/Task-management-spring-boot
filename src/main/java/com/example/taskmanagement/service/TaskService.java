package com.example.taskmanagement.service;

import java.util.List;

import com.example.taskmanagement.entity.Tasks;

public interface TaskService {
	List<Tasks> getAllTasks();
    Tasks getByTaskId(Long taskId);
    Tasks saveTask(Tasks task);
    Tasks updateTask(Long taskId, Tasks task);
    void deleteTask(Long taskId);
    boolean existsById(Long taskId);

    // new method
    List<Tasks> getTasksByUserId(Long userId);
    
    //kafka event 
    Tasks assignTaskToEmployee(Long taskId, Long employeeId);

    
    
}
