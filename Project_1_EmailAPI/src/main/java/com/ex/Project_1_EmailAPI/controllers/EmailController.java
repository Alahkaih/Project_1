package com.ex.Project_1_EmailAPI.controllers;

import com.ex.Project_1_EmailAPI.entities.Email;
import com.ex.Project_1_EmailAPI.repositories.EmailRepository;
import com.ex.Project_1_EmailAPI.services.EmailService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.net.URI;
import java.util.Optional;

/**
 * This class handles web requests for emails
 */
@RestController
@RequestMapping("emails")
public class EmailController {
    @Setter(onMethod =@__({@Autowired}))
    private EmailService emailService;

    @GetMapping
    public ResponseEntity getAllEmails() {
        return ResponseEntity.ok(emailService.getAllEmails());
    }

    @GetMapping("sender/{id}")
    public ResponseEntity getAllEmailsBySenderId(@PathVariable int id) {
        return ResponseEntity.ok(emailService.getAllEmailsBySenderId(id));
    }

    @GetMapping("receiver/{id}")
    public ResponseEntity getAllEmailsByReceiverId(@PathVariable int id) {
        return ResponseEntity.ok(emailService.getAllEmailsByReceiverId(id));
    }

    @PostMapping
    public ResponseEntity addNewEmail(@RequestBody Email email) {
        try {
            emailService.createNewEmail(email);
            return ResponseEntity.created(new URI("http://localhost/emails/" + email.getId())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving new email");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmail(@PathVariable int id) {
        try {
            emailService.deleteEmail(id);
            return ResponseEntity.ok("Email " + id + " was deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error deleting email");
        }
    }
}
