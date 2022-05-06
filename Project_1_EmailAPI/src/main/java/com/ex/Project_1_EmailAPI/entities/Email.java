package com.ex.Project_1_EmailAPI.entities;

import lombok.*;
import javax.persistence.*;

/**
 * Email object
 */
@Entity
@Table(name= "emails")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    private int id;

    @Column(name = "body")
    private String body;

    @Column(name = "_subject")
    private String subject;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private Employee sender;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private Employee receiver;
}
