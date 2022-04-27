package com.ex.Project_1.repositories;

import com.ex.Project_1.entities.Employee;
import com.ex.Project_1.entities.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
