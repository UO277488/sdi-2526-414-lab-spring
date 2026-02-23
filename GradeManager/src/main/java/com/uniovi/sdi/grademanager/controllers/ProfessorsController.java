package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.ProfessorCategory;
import com.uniovi.sdi.grademanager.services.DepartmentService;
import com.uniovi.sdi.grademanager.services.ProfessorsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/professor")
public class ProfessorsController {
    private final ProfessorsService professorsService;
    private final DepartmentService departmentService;

    public ProfessorsController(ProfessorsService professorsService, DepartmentService departmentService) {
        this.professorsService = professorsService;
        this.departmentService = departmentService;
    }

    @GetMapping("/list")
    public String getProfessorsList(Model model) {
        model.addAttribute("professorsList", professorsService.getProfessors());
        return "professor/list";
    }

    @GetMapping("/add")
    public String getProfessor(Model model) {
        model.addAttribute("categories", ProfessorCategory.values());
        model.addAttribute("departmentsList", departmentService.findAll());
        return "professor/add";
    }

    @PostMapping("/add")
    public String setProfessor(@RequestParam String dni,
                               @RequestParam String name,
                               @RequestParam String lastName,
                               @RequestParam String category,
                               @RequestParam Long departmentId,
                               RedirectAttributes redirectAttributes) {
        String messageKey = professorsService.registerProfessor(dni, name, lastName, category, departmentId);
        if ("professor.api.register.ok".equals(messageKey)) {
            redirectAttributes.addFlashAttribute("successKey", messageKey);
            return "redirect:/professor/list";
        }
        redirectAttributes.addFlashAttribute("errorKey", messageKey);
        return "redirect:/professor/add";
    }
}
