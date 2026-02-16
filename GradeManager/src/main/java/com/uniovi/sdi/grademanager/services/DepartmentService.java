package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        departmentRepository.findAll().forEach(departments::add);
        return departments;
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Department department) {
        int updatedRows = departmentRepository.updateDepartment(
                department.getId(),
                department.getName(),
                department.getCode(),
                department.getFaculty(),
                department.getPhone(),
                department.getProfessors()
        );
        if (updatedRows == 0) {
            return null;
        }
        return departmentRepository.findById(department.getId()).orElse(null);
    }

    public Department deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            return null;
        }
        departmentRepository.deleteById(id);
        return department;
    }
}
