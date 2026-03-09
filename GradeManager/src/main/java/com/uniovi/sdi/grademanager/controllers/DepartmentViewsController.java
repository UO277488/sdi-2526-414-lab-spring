package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.services.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/department")
public class DepartmentViewsController {

    private final DepartmentService departmentService;

    public DepartmentViewsController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/list")
    public String getDepartmentsList(Model model) {
        model.addAttribute("departmentsList", departmentService.findAll());
        return "department/list";
    }

    @GetMapping("/details/{id}")
    public String getDepartmentDetails(Model model, @PathVariable Long id) {
        Department department = departmentService.findById(id);
        if (department == null) {
            return "redirect:/department/list";
        }
        model.addAttribute("department", department);
        return "department/details";
    }

    @GetMapping("/add")
    public String getDepartment() {
        return "department/add";
    }

    @PostMapping("/add")
    public String setDepartment(@RequestParam String name,
                                @RequestParam String code,
                                @RequestParam String faculty,
                                @RequestParam String phone,
                                @RequestParam int professors,
                                RedirectAttributes redirectAttributes) {
        Department department = new Department(null, name, code, faculty, phone, professors);
        String messageKey = departmentService.validateDepartmentForCreate(department);
        if (messageKey != null) {
            redirectAttributes.addFlashAttribute("errorKey", messageKey);
            return "redirect:/department/add";
        }
        departmentService.saveDepartment(department);
        redirectAttributes.addFlashAttribute("successKey", "department.api.create.ok");
        return "redirect:/department/list";
    }

    @GetMapping("/edit/{id}")
    public String getEditDepartment(Model model, @PathVariable Long id) {
        Department department = departmentService.findById(id);
        if (department == null) {
            return "redirect:/department/list";
        }
        model.addAttribute("department", department);
        return "department/edit";
    }

    @PostMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String code,
                                 @RequestParam String faculty,
                                 @RequestParam String phone,
                                 @RequestParam int professors,
                                 RedirectAttributes redirectAttributes) {
        if (departmentService.findById(id) == null) {
            redirectAttributes.addFlashAttribute("errorKey", "department.api.update.error.notFound");
            return "redirect:/department/list";
        }

        Department departmentToUpdate = new Department(id, name, code, faculty, phone, professors);
        String messageKey = departmentService.validateDepartmentForUpdate(departmentToUpdate);
        if (messageKey != null) {
            redirectAttributes.addFlashAttribute("errorKey", messageKey);
            return "redirect:/department/edit/" + id;
        }
        departmentService.updateDepartment(departmentToUpdate);
        redirectAttributes.addFlashAttribute("successKey", "department.api.update.ok");
        return "redirect:/department/details/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Department deleted = departmentService.deleteDepartment(id);
        if (deleted == null) {
            redirectAttributes.addFlashAttribute("errorKey", "department.api.delete.error.notFound");
            return "redirect:/department/list";
        }
        redirectAttributes.addFlashAttribute("successKey", "department.api.delete.ok");
        return "redirect:/department/list";
    }
}
