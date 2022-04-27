package com.ex.Project_1.services;

import com.ex.Project_1.entities.Reimbursement;
import com.ex.Project_1.repositories.ReimbursementRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class ReimbursementService {



    @Setter(onMethod =@__({@Autowired}))
    private ReimbursementRepository reimbursementRepository;

    public void createNewReimbursement(Reimbursement reimbursement) {

    }
}
