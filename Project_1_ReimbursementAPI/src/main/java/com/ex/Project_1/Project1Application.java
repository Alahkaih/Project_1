package com.ex.Project_1;

import com.ex.Project_1.repositories.EmployeeRepository;
import com.ex.Project_1.repositories.ReimbursementRepository;
import com.ex.Project_1.services.EmployeeService;
import com.ex.Project_1.services.ReimbursementService;
import com.ex.Project_1.services.RestTemplateEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Project1Application {

	@Autowired
	ReimbursementRepository reimbursementRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ReimbursementService reimbursementService;

	@Autowired
	RestTemplateEmailService restTemplateEmailService;

	public static void main(String[] args) {
		SpringApplication.run(Project1Application.class, args);
	}

}
