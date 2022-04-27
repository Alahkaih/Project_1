package com.ex.Project_1_EmailAPI.repositories;

import com.ex.Project_1_EmailAPI.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    ArrayList<Email> findAllBySenderId(int sender_id);
    ArrayList<Email> findAllByReceiverId(int receiver_id);
}
