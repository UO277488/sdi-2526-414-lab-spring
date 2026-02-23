package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Professor;
import com.uniovi.sdi.grademanager.services.ProfessorsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professor")
public class ProfessorsController {
    private final ProfessorsService professorsService;

    public ProfessorsController(ProfessorsService professorsService) {
        this.professorsService = professorsService;
    }

    @GetMapping("/list")
    public String getProfessorsList() {
        List<Professor> professors = professorsService.getProfessors();
        if (professors.isEmpty()) {
            return "No hay profesores registrados.";
        }
        return professors.stream()
                .map(professor -> "DNI: " + professor.getDni()
                        + " | Nombre: " + professor.getName()
                        + " | Apellidos: " + professor.getLastName()
                        + " | Categoria: " + (professor.getCategory() == null ? "-" : professor.getCategory().getLabel())
                        + " | Departamento: " + (professor.getDepartment() == null ? "-" : professor.getDepartment().getName()))
                .collect(Collectors.joining("\n"));
    }

    @GetMapping("/add")
    public String getProfessor() {
        return "Envie POST /professor/add con: dni, name, lastName, category y departmentId.";
    }

    @PostMapping("/add")
    public String setProfessor(@RequestParam String dni,
                               @RequestParam String name,
                               @RequestParam String lastName,
                               @RequestParam String category,
                               @RequestParam Long departmentId) {
        return professorsService.registerProfessor(dni, name, lastName, category, departmentId);
    }
}
