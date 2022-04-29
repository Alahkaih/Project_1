package com.ex.Project_1.services;

import com.ex.Project_1.entities.Employee;
import com.ex.Project_1.entities.Reimbursement;
import com.ex.Project_1.exceptions.Employees.EmployeeRepositoryEmptyException;
import com.ex.Project_1.exceptions.Employees.NullPasswordException;
import com.ex.Project_1.exceptions.Reimbursements.*;
import com.ex.Project_1.repositories.ReimbursementRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ReimbursementService {



    @Setter(onMethod =@__({@Autowired}))
    private ReimbursementRepository reimbursementRepository;

    public void createNewReimbursement(Reimbursement reimbursement) throws  NullDescriptionException,
                                                                            NullEmployeeException, LongDescriptionException,
                                                                            LongOutcomeException, LongOutcomeReasonException {
        if(reimbursement.getDescription().isEmpty()) {
            throw new NullDescriptionException("Your description can't be empty");
        } else if(reimbursement.getEmployee() == null) {
            throw new NullEmployeeException("Reimbursements need an employee to reference");
        } else if(reimbursement.getDescription().length() > 50) {
            throw new LongDescriptionException("Your description can't be longer than 50 characters");
        } else if(reimbursement.getOutcome().length() > 20) {
            throw new LongOutcomeException("Your outcome can't be longer than 20 characters");
        } else if(reimbursement.getOutcomeReason().length() > 200) {
            throw new LongOutcomeReasonException("Your outcome reason can't be longer than 200 characters");
        } else {
            reimbursementRepository.save(reimbursement);
        }
    }

    public List<Reimbursement> getAllReimbursements() {
        List<Reimbursement> reimbursementList = reimbursementRepository.findAll();
        if(reimbursementList.isEmpty()) {
            throw new ReimbursementRepositoryEmptyException("Could not find any reimbursements");
        } else {
            return reimbursementList;
        }
    }

    public void deleteReimbursement(int id) {
        reimbursementRepository.delete(findReimbursementById(id));
    }

    public Reimbursement findReimbursementById(int id) throws ReimbursementNotFoundException{
        Optional<Reimbursement> reimbursement = reimbursementRepository.findById(id);
        if(reimbursement.isPresent()) {
            return reimbursement.get();
        } else {
            throw new ReimbursementNotFoundException("Reimbursement was not found with ID: " + id);
        }
    }

    public List<Reimbursement> findAllReimbursementsByEmployeeId(int id) {
        return reimbursementRepository.findAllByEmployee_Id(id);
    }

    public void updateReimbursement(int id) {

    }
}
