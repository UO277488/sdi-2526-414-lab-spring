package com.uniovi.sdi.grademanager.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;

@Entity
public class Professor {
    @Id
    @GeneratedValue
    private Long id;
    private String dni;
    private String name;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private ProfessorCategory category;

    @ManyToOne
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Professor() {
    }

    public Professor(Long id, String dni, String name, String lastName) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ProfessorCategory getCategory() {
        return category;
    }

    public void setCategory(ProfessorCategory category) {
        this.category = category;
    }
}
