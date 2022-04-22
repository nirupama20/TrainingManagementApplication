package com.example.registrationportal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.registrationportal.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Integer>{

	@Query("from Task as t where t.trainee.tid=:id")
	public List<Task> findByTrainee(@Param("id")int id);
}
