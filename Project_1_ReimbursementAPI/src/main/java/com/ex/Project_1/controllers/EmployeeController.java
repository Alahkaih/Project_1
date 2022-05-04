package com.ex.Project_1.controllers;

import com.ex.Project_1.entities.Employee;
import com.ex.Project_1.repositories.EmployeeRepository;
import com.ex.Project_1.services.EmployeeService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * This class handles web requests for employees
 */
@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Setter(onMethod = @__({@Autowired}))
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("{id}")
    public ResponseEntity getEmployeeById(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity addNewEmployee(@RequestBody Employee employee) {
        try {
            employeeService.createNewEmployee(employee);
            return ResponseEntity.created(new URI("http://localhost/employees/" + employee.getId())).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error creating new employee");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee " + id + " was deleted.");
    }
}
