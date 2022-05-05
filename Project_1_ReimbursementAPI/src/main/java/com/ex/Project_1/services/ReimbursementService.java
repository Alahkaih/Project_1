package com.ex.Project_1.services;

import com.ex.Project_1.entities.Employee;
import com.ex.Project_1.entities.Reimbursement;
import com.ex.Project_1.exceptions.Reimbursements.*;
import com.ex.Project_1.repositories.ReimbursementRepository;
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
public class ReimbursementService {

    @Setter(onMethod =@__({@Autowired}))
    private ReimbursementRepository reimbursementRepository;

    @Setter(onMethod =@__({@Autowired}))
    private EmployeeService employeeService;

    @Setter(onMethod =@__({@Autowired}))
    private RestTemplateEmailService restTemplateEmailService;

    final Logger logger = LoggerFactory.getLogger(Reimbursement.class);

    public Reimbursement createNewReimbursement(Reimbursement reimbursement) throws  NullDescriptionException,
                                                                            NullEmployeeException, LongDescriptionException,
                                                                            LongOutcomeException, LongOutcomeReasonException {
        logger.info("Attempting to create new reimbursement");
        if(reimbursement.getDescription() == null) {
            logger.info("Failed to create new reimbursement: Null Description");
            throw new NullDescriptionException("Your description can't be empty");
        } else if(reimbursement.getEmployee() == null) {
            logger.info("Failed to create new reimbursement: Null Employee reference");
            throw new NullEmployeeException("Reimbursements need an employee to reference");
        } else if(reimbursement.getDescription().length() > 50) {
            logger.info("Failed to create new reimbursement: Long Description");
            throw new LongDescriptionException("Your description can't be longer than 50 characters");
        } else {
            Reimbursement newReimbursement = reimbursementRepository.save(reimbursement);
            sendEmail(
                    9,
                    reimbursement.getEmployee().getId(),
                    "Reimbursement received",
                    "This is confirmation for your reimbursement of $" +
                            reimbursement.getReimbursementAmount() +
                            " for " +
                            reimbursement.getDescription()
            );
            logger.info("Successfully created new reimbursement");
            return newReimbursement;
        }
    }

    public List<Reimbursement> getAllReimbursements() {
        logger.info("Attempting to get all reimbursements");
        List<Reimbursement> reimbursementList = reimbursementRepository.findAll();
        if(reimbursementList.isEmpty()) {
            logger.info("Failed to get all reimbursements: No Reimbursements");
            throw new ReimbursementRepositoryEmptyException("Could not find any reimbursements");
        } else {
            logger.info("Successfully got all reimbursements");
            return reimbursementList;
        }
    }

    public void deleteReimbursement(int id) {
        logger.info("Attempting to delete reimbursement with id: " + id);
        if(findReimbursementById(id) == null) {
            logger.info("Reimbursement with id: " + id + " does not exist");
            throw new NullPointerException("Cannot delete a reimbursement that does not exist");
        } else {
            reimbursementRepository.delete(findReimbursementById(id));
            logger.info("Successfully deleted reimbursement with id: " + id);
        }
    }

    public Reimbursement findReimbursementById(int id) throws ReimbursementNotFoundException{
        logger.info("Attempting to find reimbursements with id: " + id);
        Optional<Reimbursement> reimbursement = reimbursementRepository.findById(id);
        if(reimbursement.isPresent()) {
            logger.info("Successfully found reimbursement with id: " + id);
            return reimbursement.get();
        } else {
            logger.info("Reimbursement with id " + id + " not found");
            throw new ReimbursementNotFoundException("Reimbursement was not found with ID: " + id);
        }
    }

    public List<Reimbursement> findAllReimbursementsByEmployeeId(int id) {
        logger.info("Attempting to find all reimbursements for employee " + id);
        if(reimbursementRepository.findAllByEmployee_Id(id).isEmpty()) {
            logger.info("Employee " + id + " has no reimbursements");
            throw new NullPointerException("This employee has no reimbursements");
        } else {
            logger.info("Successfully found reimbursements for employee " + id);
            return reimbursementRepository.findAllByEmployee_Id(id);
        }

    }

    public boolean updateReimbursement(Reimbursement newReimbursement, int oldReimbursementId) {
        logger.info("Attempting to update reimbursement");

        Reimbursement tempReimbursement = newReimbursement;
        newReimbursement = findReimbursementById(oldReimbursementId);
        Employee manager = employeeService.findEmployeeById(tempReimbursement.getManager().getId());

        newReimbursement.setOutcome(tempReimbursement.getOutcome());
        newReimbursement.setOutcomeReason(tempReimbursement.getOutcomeReason());
        newReimbursement.setManager(manager);
        newReimbursement.setActive(false);

        if(manager.isManager()) {
            reimbursementRepository.updateReimbursement(newReimbursement, oldReimbursementId);
            sendEmail(
                    newReimbursement.getManager().getId(),
                    newReimbursement.getEmployee().getId(),
                    "Reimbursement status: " + newReimbursement.getOutcome(),
                    newReimbursement.getOutcomeReason()
            );
            logger.info("Employee " + manager.getId() + " is a manager and reimbursement successfully updated");
            return true;
        } else {
            logger.info("Employee " + manager.getId() + " is not a manager");
            return false;
        }
    }

    public void reassignReimbursement(int reimbursementId, int newEmployeeId) {
        Reimbursement tempReimbursement = findReimbursementById(reimbursementId);
        tempReimbursement.setEmployee(employeeService.findEmployeeById(newEmployeeId));
        reimbursementRepository.updateReimbursement(tempReimbursement, reimbursementId);
    }

    public void sendEmail(int managerId, int employeeId, String subject, String body) {
        restTemplateEmailService.sendEmail(
                employeeService.findEmployeeById(managerId),
                employeeService.findEmployeeById(employeeId),
                subject,
                body
        );
    }
}
