package com.ex.Project_1.controllers;

import com.ex.Project_1.entities.Reimbursement;
import com.ex.Project_1.repositories.ReimbursementRepository;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("reimbursements")
public class ReimbursementController {

    @Setter(onMethod =@__({@Autowired}))
    private ReimbursementRepository reimbursementRepository;

    @GetMapping
    public ResponseEntity getAllReimbursements() {
        return ResponseEntity.ok(reimbursementRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity getReimbursementById(@PathVariable int id) {
        Optional<Reimbursement> reimbursement = reimbursementRepository.findById(id);

        if(reimbursement.isPresent()) {
            return ResponseEntity.ok(reimbursement);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity addNewReimbursement(@RequestBody Reimbursement reimbursement) {
        try {
            reimbursementRepository.save(reimbursement);
            return ResponseEntity.created(new URI("http://localhost/reimbursements/" + reimbursement.getId())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving new reimbursement");
        }
    }

    @GetMapping("employees/{id}")
    public ResponseEntity getAllReimbursementsById(@PathVariable int id) {
        return ResponseEntity.ok(reimbursementRepository.findAllByEmployee_Id(id));
    }

    @PutMapping("manage/{id}")
    public ResponseEntity manageReimbursement(@PathVariable int id, @RequestBody Reimbursement reimbursementData) {
        Optional<Reimbursement> reimbursement = reimbursementRepository.findById(id);

        if(reimbursement.isPresent()) {
            Reimbursement newReimbursement = new Reimbursement(
                    reimbursement.get().getId(),
                    false,
                    reimbursementData.getOutcome(),
                    reimbursementData.getOutcomeReason(),
                    reimbursement.get().getDescription(),
                    reimbursement.get().getReimbursementAmount(),
                    reimbursement.get().getEmployee(),
                    reimbursementData.getManager()
            );
//            reimbursement.get().setActive(false);
//            reimbursement.get().setOutcome(reimbursementData.getOutcome());
//            reimbursement.get().setOutcomeReason(reimbursementData.getOutcomeReason());
//            reimbursement.get().setManager(reimbursementData.getManager());
//            newReimbursement = reimbursement.get();

            if(reimbursementData.getManager().isManager()) {
                reimbursementRepository.updateReimbursement(newReimbursement, reimbursement.get().getId());
                return ResponseEntity.accepted().body("Reimbursement successfully managed");
            }else {
                return ResponseEntity.internalServerError().body("Employee " + reimbursementData.getManager().getId() + " is not a manager\n" + reimbursementData.getManager());
            }
        }
        return ResponseEntity.internalServerError().body("Error managing reimbursement");

    }
}
