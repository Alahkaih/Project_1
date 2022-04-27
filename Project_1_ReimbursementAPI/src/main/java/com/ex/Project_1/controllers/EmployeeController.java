package com.ex.Project_1.controllers;

import com.ex.Project_1.entities.Employee;
import com.ex.Project_1.repositories.EmployeeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Setter(onMethod = @__({@Autowired}))
    private EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @PostMapping
    public ResponseEntity addNewEmployee(@RequestBody Employee employee) {
        try {
            employeeRepository.save(employee);
            return ResponseEntity.created(new URI("http://localhost/employees/" + employee.getId())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving new employee");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmployee(@PathVariable int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return ResponseEntity.ok("Employee " + id + " was deleted.");
        }
        return ResponseEntity.internalServerError().body("Error deleting employee");
    }
}
