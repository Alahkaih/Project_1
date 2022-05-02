package com.ex.Project_1.controllers;

import com.ex.Project_1.entities.Reimbursement;
import com.ex.Project_1.exceptions.Reimbursements.ReimbursementNotFoundException;
import com.ex.Project_1.repositories.ReimbursementRepository;
import com.ex.Project_1.services.ReimbursementService;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

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
    public ResponseEntity manageReimbursement(@PathVariable int id, @RequestBody Reimbursement reimbursementData) {
        Optional<Reimbursement> reimbursement = reimbursementRepository.findById(id);

        if(reimbursement.isPresent()) {
//            reimbursement.get().setManager(reimbursementData.getManager());
//            reimbursementRepository.updateReimbursement(reimbursement.get(), reimbursement.get().getId());
//            System.out.println(reimbursement.get());
//            reimbursement = reimbursementRepository.findById(id);
//            Reimbursement newReimbursement = new Reimbursement(
//                    reimbursement.get().getId(),
//                    false,
//                    reimbursementData.getOutcome(),
//                    reimbursementData.getOutcomeReason(),
//                    reimbursement.get().getDescription(),
//                    reimbursement.get().getReimbursementAmount(),
//                    reimbursement.get().getEmployee(),
//                    reimbursementData.getManager()
//            );
            reimbursement.get().setActive(false);
            reimbursement.get().setOutcome(reimbursementData.getOutcome());
            reimbursement.get().setOutcomeReason(reimbursementData.getOutcomeReason());
            reimbursement.get().setManager(reimbursementData.getManager());
            Reimbursement newReimbursement = reimbursement.get();

            if(reimbursementData.getManager().isManager() || true) {
                reimbursementRepository.updateReimbursement(newReimbursement, reimbursement.get().getId());
                return ResponseEntity.accepted().body("Reimbursement successfully managed\n" + newReimbursement.getManager() + "\n" + reimbursement.get().getManager());
            }else {
                return ResponseEntity.internalServerError().body("Employee " + reimbursementData.getManager().getId() + " is not a manager\n" + reimbursementData.getManager());
            }
        }
        return ResponseEntity.internalServerError().body("Error managing reimbursement");

    }
}
