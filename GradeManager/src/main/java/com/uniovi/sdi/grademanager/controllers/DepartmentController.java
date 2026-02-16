package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getList() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable Long id) {
        Department deleted = departmentService.deleteDepartment(id);
        return ResponseEntity.ok(deleted);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department created = departmentService.saveDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Department departmentToUpdate = new Department(
                id,
                department.getName(),
                department.getCode(),
                department.getFaculty(),
                department.getPhone(),
                department.getProfessors()
        );
        Department updated = departmentService.updateDepartment(departmentToUpdate);
        return ResponseEntity.ok(updated);
    }

    //PatchMapping no es para esto: es para cambios PARCIALES:
    /*
    @PatchMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Department departmentToUpdate = new Department(
                id,
                department.getName(),
                department.getCode(),
                department.getFaculty(),
                department.getPhone(),
                department.getProfessors()
        );
        Department updated = departmentService.updateDepartment(departmentToUpdate);
        return ResponseEntity.ok(updated);
    }*/
}
