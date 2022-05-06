package com.ex.Project_1_EmailAPI.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Employee object
 */
@Entity
@Table(name= "Employees")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "AUTO_INCREMENT")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "user_password")
    private String password;

    @Column(name = "is_manager")
    private boolean isManager;
}
