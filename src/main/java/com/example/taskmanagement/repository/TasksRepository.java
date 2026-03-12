package com.example.taskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.taskmanagement.entity.Tasks;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
	@Query("SELECT t FROM Tasks t WHERE t.createdBy.id = :userId")
	List<Tasks> findByCreatedById(@Param("userId") Long userId);

}
