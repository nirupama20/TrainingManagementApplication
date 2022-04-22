package com.example.registrationportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.registrationportal.entity.Trainee;

public interface TraineeRepo extends JpaRepository<Trainee, Integer>{

	Trainee findByTemail(String temail);
	Trainee findByTemailAndTpassword(String temail,String tpassword);
}
