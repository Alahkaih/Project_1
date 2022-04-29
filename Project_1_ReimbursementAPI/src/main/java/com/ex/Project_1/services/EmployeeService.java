package com.ex.Project_1.services;

import com.ex.Project_1.entities.Employee;
import com.ex.Project_1.exceptions.Employees.*;
import com.ex.Project_1.repositories.EmployeeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Service layer between the repository and controller
 */
public class EmployeeService {

    @Setter(onMethod = @__({@Autowired}))
    private EmployeeRepository employeeRepository;

    public void createNewEmployee(Employee employee) throws NullUsernameException, LongUsernameException,
                                                            NullPasswordException, LongPasswordException {
        if(employee.getUsername().isEmpty()) {
            throw new NullUsernameException("You can't have a null username");
        } else if(employee.getUsername().length() > 20) {
            throw new LongUsernameException("Your username needs to be 20 or less characters");
        } else if(employee.getPassword().isEmpty()) {
            throw new NullPasswordException("You can't have a null password");
        } else if(employee.getPassword().length() > 20) {
            throw new LongPasswordException("Your password needs to be 20 or less characters");
        } else {
            employeeRepository.save(employee);
        }
    }

    public List<Employee> getAllEmployees() throws EmployeeRepositoryEmptyException {
        List<Employee> employeeList = employeeRepository.findAll();
        if(employeeList.isEmpty()) {
            throw new EmployeeRepositoryEmptyException("Could not find any employees");
        } else {
            return employeeList;
        }
    }

    public void deleteEmployee(int id) {
        employeeRepository.delete(findEmployeeById(id));
    }

    public Employee findEmployeeById(int id) throws UserNotFoundException {

        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new UserNotFoundException("User was not found with ID: " + id);
        }
    }
}
