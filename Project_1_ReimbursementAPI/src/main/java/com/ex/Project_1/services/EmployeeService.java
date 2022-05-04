package com.ex.Project_1.services;

import com.ex.Project_1.entities.Employee;
import com.ex.Project_1.exceptions.Employees.*;
import com.ex.Project_1.repositories.EmployeeRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer between the repository and controller
 */
@Service
public class EmployeeService {

    @Setter(onMethod = @__({@Autowired}))
    private EmployeeRepository employeeRepository;
    final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public void createNewEmployee(Employee employee) throws NullUsernameException, LongUsernameException,
                                                            NullPasswordException, LongPasswordException {
        logger.debug("Attempting to create a new employee");
        if(employee.getUsername().isEmpty()) {
            logger.debug("Failed to create new employee: Null Username");
            throw new NullUsernameException("You can't have a null username");
        } else if(employee.getUsername().length() > 20) {
            logger.debug("Failed to create new employee: Long Username");
            throw new LongUsernameException("Your username needs to be 20 or less characters");
        } else if(employee.getPassword().isEmpty()) {
            logger.debug("Failed to create new employee: Null Password");
            throw new NullPasswordException("You can't have a null password");
        } else if(employee.getPassword().length() > 20) {
            logger.debug("Failed to create new employee: Long Password");
            throw new LongPasswordException("Your password needs to be 20 or less characters");
        } else {
            employeeRepository.save(employee);
            logger.debug("Successfully created new employee");
        }
    }

    public List<Employee> getAllEmployees() throws EmployeeRepositoryEmptyException {
        logger.debug("Attempting to get all employees");
        List<Employee> employeeList = employeeRepository.findAll();
        if(employeeList.isEmpty()) {
            logger.debug("Employee list is empty");
            throw new EmployeeRepositoryEmptyException("Could not find any employees");
        } else {
            logger.debug("Successfully got list of employees");
            return employeeList;
        }
    }

    /**
     * Deletes employee by their id
     * @param id employee's id
     * @throws NullPointerException for user not found
     */
    public void deleteEmployee(int id) throws NullPointerException{
        logger.debug("Attempting to delete employee with id: " + id);
        if(findEmployeeById(id) == null) {
            logger.debug("Employee with id: " + id + " does not exist");
            throw new NullPointerException("Cannot delete a user that does not exist");
        } else {
            employeeRepository.delete(findEmployeeById(id));
            logger.debug("Successfully deleted employee with id: " + id);
        }

    }

    public Employee findEmployeeById(int id) throws UserNotFoundException {
        logger.debug("Attempting to find employee with id: " + id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            logger.debug("Employee with id " + id + "does not exist");
            return employee.get();
        } else {
            logger.debug("Employee with id " + id + "was found successfully");
            throw new UserNotFoundException("User was not found with ID: " + id);
        }
    }
}
