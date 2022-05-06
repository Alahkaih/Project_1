package com.ex.Project_1.repositories;

import com.ex.Project_1.entities.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

/**
 * Repository for storing Reimbursements in the database
 */
@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Integer> {

    ArrayList<Reimbursement> findAllByEmployee_Id(int employee_id);

    @Transactional
    @Modifying
    @Query("update Reimbursement r set r = :reimbursement where r.id = :id")
    public void updateReimbursement(@Param("reimbursement") Reimbursement reimbursement, @Param("id") int id);
}
