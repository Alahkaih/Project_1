package com.ex.Project_1.repositories;

import com.ex.Project_1.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for storing Employees in the database
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
