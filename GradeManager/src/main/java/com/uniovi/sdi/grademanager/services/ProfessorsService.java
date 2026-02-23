package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.entities.Professor;
import com.uniovi.sdi.grademanager.entities.ProfessorCategory;
import com.uniovi.sdi.grademanager.repositories.DepartmentRepository;
import com.uniovi.sdi.grademanager.repositories.ProfessorsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorsService {
    private final ProfessorsRepository professorsRepository;
    private final DepartmentRepository departmentRepository;

    public ProfessorsService(ProfessorsRepository professorsRepository, DepartmentRepository departmentRepository) {
        this.professorsRepository = professorsRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Professor> getProfessors() {
        List<Professor> professors = new ArrayList<>();
        professorsRepository.findAll().forEach(professors::add);
        return professors;
    }

    public void addProfessor(Professor professor) {
        professorsRepository.save(professor);
    }

    public String registerProfessor(String dni, String name, String lastName, String categoryValue, Long departmentId) {
        if (dni == null || dni.isBlank() || name == null || name.isBlank() || lastName == null || lastName.isBlank()) {
            return "professor.api.register.error.required";
        }

        if (professorsRepository.findByDni(dni) != null) {
            return "professor.api.register.error.duplicateDni";
        }

        ProfessorCategory category = ProfessorCategory.fromValue(categoryValue);
        if (category == null) {
            return "professor.api.register.error.invalidCategory";
        }

        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department == null) {
            return "professor.api.register.error.departmentNotFound";
        }

        Professor professor = new Professor();
        professor.setDni(dni);
        professor.setName(name);
        professor.setLastName(lastName);
        professor.setCategory(category);
        professor.setDepartment(department);
        addProfessor(professor);

        return "professor.api.register.ok";
    }
}
