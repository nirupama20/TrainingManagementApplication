package com.example.registrationportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.registrationportal.entity.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer, Integer>{

	Trainer findByEmail(String email);
	Trainer findByEmailAndPassword(String email,String password);
}
