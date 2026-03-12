package com.example.taskmanagement.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEvent {

    private Long taskId;
    private Long employeeId;
    private String eventType;   // TASK_ASSIGNED
}
