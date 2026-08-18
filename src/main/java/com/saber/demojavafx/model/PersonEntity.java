package com.saber.demojavafx.model;

import lombok.Data;
import jakarta.persistence.*;


import java.time.LocalDateTime;
@Entity
@Table(name = "persons")
@Data
@NamedQuery(name = "Person.findAll",query = "select p from PersonEntity p")
@NamedQuery(name = "Person.findById",query = "select p from PersonEntity p where p.id=:id")
@NamedQuery(name = "Person.findByNationalCode",query = "select p from PersonEntity p where p.nationalCode=:nationalCode")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "firstname", length = 75)
    private String firstname;
    @Column(name = "lastname", length = 85)
    private String lastname;
    @Column(name = "nationalCode", length = 10, unique = true)
    private String nationalCode;
    @Column(name = "age")
    private Integer age;
    @Column(name = "mobile", length = 11)
    private String mobile;
    @Column(name = "email")
    private String email;
    @Column(name = "createdAt", length = 50)
    private LocalDateTime createdAt;
    @Column(name = "updatedAt", length = 50)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
