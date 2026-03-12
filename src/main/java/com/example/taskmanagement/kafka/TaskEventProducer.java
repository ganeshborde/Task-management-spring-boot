package com.example.taskmanagement.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.events.TaskEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskEventProducer {

    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

    public TaskEventProducer(KafkaTemplate<String, TaskEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

  //  @Transactional("kafkaTransactionManager")
    public void publishTaskAssigned(TaskEvent event) {

    	 kafkaTemplate.executeInTransaction(kt -> {

             log.info("Sending TASK_ASSIGNED event");
             kt.send("task-events", event.getEmployeeId().toString(), event);

             log.info("Sending AUDIT event");
             kt.send("task-audit-events",
                     event.getEmployeeId().toString(),
                     new TaskEvent(
                             event.getTaskId(),
                             event.getEmployeeId(),
                             "AUDIT_LOG"
                     ));

             log.info("Sending NOTIFICATION event");
             kt.send("task-notification-events",
                     event.getEmployeeId().toString(),
                     new TaskEvent(
                             event.getTaskId(),
                             event.getEmployeeId(),
                             "NOTIFY_EMPLOYEE"
                     ));

             return true;
         });
    	 
         log.info("All messages sent in one transaction");
    }
}
