package com.ex.Project_1.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name= "Reimbursements")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    private int id;

    @Column(name = "_active")
    private boolean active;

    @Column(name = "outcome")
    private String outcome;

    @Column(name = "outcome_reason")
    private String outcomeReason;

    @Column(name = "_description")
    private String description;

    @Column(name = "amount")
    private double reimbursementAmount;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Employee manager;
}
