package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Professor;
import com.uniovi.sdi.grademanager.services.ProfessorsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfessorsController {
    private final ProfessorsService professorsService;

    public ProfessorsController(ProfessorsService professorsService) {
        this.professorsService = professorsService;
    }

    @GetMapping("/professor/list")
    public String getProfessorsList(Model model) {
        model.addAttribute("professorsList", professorsService.getProfessors());
        return "professor/list";
    }

    @GetMapping("/professor/add")
    public String getProfessor() {
        return "professor/add";
    }

    @PostMapping("/professor/add")
    public String setProfessor(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return "redirect:/professor/list";
    }
}
