package com.ex.Project_1_EmailAPI.controllers;

import com.ex.Project_1_EmailAPI.entities.Email;
import com.ex.Project_1_EmailAPI.repositories.EmailRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("emails")
public class EmailController {
    @Setter(onMethod =@__({@Autowired}))
    private EmailRepository emailRepository;

    @GetMapping
    public ResponseEntity getAllEmails() {
        return ResponseEntity.ok(emailRepository.findAll());
    }

    @GetMapping("sender/{id}")
    public ResponseEntity getAllEmailsBySenderId(@PathVariable int id) {
        return ResponseEntity.ok(emailRepository.findAllBySenderId(id));
    }

    @GetMapping("receiver/{id}")
    public ResponseEntity getAllEmailsByReceiverId(@PathVariable int id) {
        return ResponseEntity.ok(emailRepository.findAllByReceiverId(id));
    }

    @PostMapping
    public ResponseEntity addNewEmail(@RequestBody Email email) {
        try {
            emailRepository.save(email);
            return ResponseEntity.created(new URI("http://localhost/emails/" + email.getId())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving new email");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmail(@PathVariable int id) {
        Optional<Email> email = emailRepository.findById(id);
        if(email.isPresent()) {
            emailRepository.delete(email.get());
            return ResponseEntity.ok("Email " + id + " was deleted.");
        }
        return ResponseEntity.internalServerError().body("Error deleting email");
    }
}
