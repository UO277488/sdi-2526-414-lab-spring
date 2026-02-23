package com.uniovi.sdi.grademanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String code;
    private String faculty;
    private String phone;
    private int professors;
    public Department() {
    }
    public Department(Long id, String name, String code, String faculty, String phone, int professors) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.faculty = faculty;
        this.phone = phone;
        this.professors = professors;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getFaculty() {
        return faculty;
    }
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getProfessors() {
        return professors;
    }
    public void setProfessors(int professors) {
        this.professors = professors;
    }

}
