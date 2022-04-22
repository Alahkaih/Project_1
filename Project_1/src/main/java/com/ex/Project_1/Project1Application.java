package com.ex.Project_1;

import com.ex.Project_1.repositories.ReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Project1Application {

	@Autowired
	ReimbursementRepository reimbursementRepository;

	public static void main(String[] args) {
		SpringApplication.run(Project1Application.class, args);
	}

}
