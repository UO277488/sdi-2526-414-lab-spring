package com.uniovi.sdi.grademanager.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String dni;
    private String name;
    private String lastName;
    private String role;

    private String password;
    @Transient //propiedad que no se almacena en la tabla.
    private String passwordConfirm;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Mark> marks = new HashSet<>();

    public User() {
    }

    public User(String dni, String name, String lastName) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    public void addMark(Mark mark) {
        marks.add(mark);
        mark.setUser(this);
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
