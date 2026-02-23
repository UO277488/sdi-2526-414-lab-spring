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

    public String validateDepartmentForCreate(Department department) {
        if (department == null) {
            return "department.validation.required";
        }
        if (!isTrimmedAndNotBlank(department.getName())) {
            return "department.validation.name.trimmedNotBlank";
        }
        if (!isTrimmedAndNotBlank(department.getFaculty())) {
            return "department.validation.faculty.trimmedNotBlank";
        }
        if (!isTrimmedAndNotBlank(department.getPhone())) {
            return "department.validation.phone.trimmedNotBlank";
        }
        String code = department.getCode();
        if (code == null || code.length() != 9 || !Character.isLetter(code.charAt(code.length() - 1))) {
            return "department.validation.code.format";
        }
        if (departmentRepository.existsByCodeIgnoreCase(code)) {
            return "department.validation.code.duplicate";
        }
        return null;
    }

    public Department updateDepartment(Department department) {
        Department oldDepartment = findById(department.getId());
        if (oldDepartment == null) {
            return null;
        }
        oldDepartment.setName(department.getName());
        return departmentRepository.save(department);
    }

    public Department deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            return null;
        }
        departmentRepository.deleteById(id);
        return department;
    }

    private boolean isTrimmedAndNotBlank(String value) {
        return value != null && !value.isBlank() && value.equals(value.trim());
    }
}
