package com.ex.Project_1_EmailAPI;

import com.ex.Project_1_EmailAPI.repositories.EmailRepository;
import com.ex.Project_1_EmailAPI.services.EmailService;
import com.ex.Project_1_EmailAPI.services.RestTemplateEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Project1EmailApiApplication {

	@Autowired
	EmailRepository emailRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	RestTemplateEmployeeService restTemplateEmployeeService;

	public static void main(String[] args) {
		SpringApplication.run(Project1EmailApiApplication.class, args);
	}

}
