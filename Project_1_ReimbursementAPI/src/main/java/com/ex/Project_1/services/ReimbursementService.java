package com.ex.Project_1.services;

import com.ex.Project_1.entities.Employee;
import com.ex.Project_1.entities.Reimbursement;
import com.ex.Project_1.exceptions.Employees.EmployeeRepositoryEmptyException;
import com.ex.Project_1.exceptions.Employees.NullPasswordException;
import com.ex.Project_1.exceptions.Reimbursements.*;
import com.ex.Project_1.repositories.ReimbursementRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Service layer between the repository and controller
 */
public class ReimbursementService {

    @Setter(onMethod =@__({@Autowired}))
    private ReimbursementRepository reimbursementRepository;
    final Logger logger = LoggerFactory.getLogger(Reimbursement.class);

    public void createNewReimbursement(Reimbursement reimbursement) throws  NullDescriptionException,
                                                                            NullEmployeeException, LongDescriptionException,
                                                                            LongOutcomeException, LongOutcomeReasonException {
        logger.debug("Attempting to create new reimbursement");
        if(reimbursement.getDescription().isEmpty()) {
            logger.debug("Failed to create new reimbursement: Null Description");
            throw new NullDescriptionException("Your description can't be empty");
        } else if(reimbursement.getEmployee() == null) {
            logger.debug("Failed to create new reimbursement: Null Employee reference");
            throw new NullEmployeeException("Reimbursements need an employee to reference");
        } else if(reimbursement.getDescription().length() > 50) {
            logger.debug("Failed to create new reimbursement: Long Description");
            throw new LongDescriptionException("Your description can't be longer than 50 characters");
        } else if(reimbursement.getOutcome().length() > 20) {
            logger.debug("Failed to create new reimbursement: Long outcome");
            throw new LongOutcomeException("Your outcome can't be longer than 20 characters");
        } else if(reimbursement.getOutcomeReason().length() > 200) {
            logger.debug("Failed to create new reimbursement: Long outcome reason");
            throw new LongOutcomeReasonException("Your outcome reason can't be longer than 200 characters");
        } else {
            reimbursementRepository.save(reimbursement);
            logger.debug("Successfully created new reimbursement");
        }
    }

    public List<Reimbursement> getAllReimbursements() {
        logger.debug("Attempting to get all reimbursements");
        List<Reimbursement> reimbursementList = reimbursementRepository.findAll();
        if(reimbursementList.isEmpty()) {
            logger.debug("Failed to get all reimbursements: No Reimbursements");
            throw new ReimbursementRepositoryEmptyException("Could not find any reimbursements");
        } else {
            logger.debug("Successfully got all reimbursements");
            return reimbursementList;
        }
    }

    public void deleteReimbursement(int id) {
        logger.debug("Attempting to delete reimbursement with id: " + id);
        if(findReimbursementById(id) == null) {
            logger.debug("Reimbursement with id: " + id + " does not exist");
            throw new NullPointerException("Cannot delete a reimbursement that does not exist");
        } else {
            reimbursementRepository.delete(findReimbursementById(id));
            logger.debug("Successfully deleted reimbursement with id: " + id);
        }
    }

    public Reimbursement findReimbursementById(int id) throws ReimbursementNotFoundException{
        logger.debug("Attempting to find reimbursements with id: " + id);
        Optional<Reimbursement> reimbursement = reimbursementRepository.findById(id);
        if(reimbursement.isPresent()) {
            logger.debug("Reimbursement with id " + id + " not found");
            return reimbursement.get();
        } else {
            logger.debug("Successfully found reimbursement with id: " + id);
            throw new ReimbursementNotFoundException("Reimbursement was not found with ID: " + id);
        }
    }

    public List<Reimbursement> findAllReimbursementsByEmployeeId(int id) {
        logger.debug("Attempting to find all reimbursements for employee " + id);
        if(reimbursementRepository.findAllByEmployee_Id(id).isEmpty()) {
            logger.debug("Employee " + id + " has no reimbursements");

            throw new NullPointerException("This employee has no reimbursements");
        } else {
            logger.debug("Successfully found reimbursements for employee " + id);
            return reimbursementRepository.findAllByEmployee_Id(id);
        }

    }

    public void updateReimbursement(int id) {
        logger.debug("Attempting to update reimbursement");

    }
}
