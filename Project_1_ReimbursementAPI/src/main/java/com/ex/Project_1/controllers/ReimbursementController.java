package com.ex.Project_1.controllers;

import com.ex.Project_1.entities.Employee;
import com.ex.Project_1.entities.Reimbursement;
import com.ex.Project_1.exceptions.Reimbursements.ReimbursementNotFoundException;
import com.ex.Project_1.services.ReimbursementService;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * This class handles web requests for reimbursements
 */
@RestController
@RequestMapping("reimbursements")
public class ReimbursementController {

    @Setter(onMethod =@__({@Autowired}))
    private ReimbursementService reimbursementService;

    @GetMapping
    public ResponseEntity getAllReimbursements() {
        return ResponseEntity.ok(reimbursementService.getAllReimbursements());
    }

    @GetMapping("{id}")
    public ResponseEntity getReimbursementById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(reimbursementService.findReimbursementById(id));
        } catch (ReimbursementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity addNewReimbursement(@RequestBody Reimbursement reimbursement) {
        try {
            reimbursementService.createNewReimbursement(reimbursement);
            return ResponseEntity.created(new URI("http://localhost/reimbursements/" + reimbursement.getId())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving new reimbursement");
        }
    }

    @GetMapping("employees/{id}")
    public ResponseEntity getAllReimbursementsByEmployeeId(@PathVariable int id) {
        return ResponseEntity.ok(reimbursementService.findAllReimbursementsByEmployeeId(id));
    }

    @PutMapping("manage/{id}")
    public ResponseEntity manageReimbursement(@RequestBody Reimbursement reimbursementData, @PathVariable int id) {
        if(reimbursementService.updateReimbursement(reimbursementData, id)) {
            return ResponseEntity.ok("Reimbursement successfully updated");
        } else {
            return ResponseEntity.internalServerError().body("Error managing reimbursement");
        }
    }

    @PutMapping("reassign/{id}")
    public ResponseEntity reassignReimbursement(@RequestBody Employee employee, @PathVariable int id) {
        reimbursementService.reassignReimbursement(id, employee.getId());
        return ResponseEntity.ok("Reimbursement " + id + " successfully reassigned to employee " + employee.getId());
    }
}
