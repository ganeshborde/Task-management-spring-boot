
package com.example.taskmanagement.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 255)
	private String title;

	@Size(max = 1000)
	private String description;

	@Pattern(regexp = "Low|Medium|High")
	private String priority;

	@Pattern(regexp = "Not Started|Completed|ASSIGNED", message = "Status must be Not Started, Completed, or ASSIGNED")
	private String status;

	private LocalDateTime dueDate;

	@ElementCollection
	@CollectionTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"))
	@Column(name = "tag")
	private List<String> tags;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	// Logical relationship only
	@Column(name = "assigned_employee_id")
	private Long assignedEmployeeId;

	// User belongs to same service → JPA relationship OK
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
}
