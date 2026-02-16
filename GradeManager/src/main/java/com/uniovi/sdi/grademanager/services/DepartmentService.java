package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    //private DepartmentRepository departmentRepository

    public List<Department> findAll() {
        Department department1 = new Department(1L, "Informatica", "INF", "Escuela de Ingenieria Informatica", 985100001, 40);
        Department department2 = new Department(2L, "Matematicas", "MAT", "Facultad de Ciencias", 985100002, 28);
        Department department3 = new Department(3L, "Fisica", "FIS", "Facultad de Ciencias", 985100003, 22);
        Department department4 = new Department(4L, "Quimica", "QUI", "Facultad de Ciencias", 985100004, 24);
        Department department5 = new Department(5L, "Economia", "ECO", "Facultad de Economia y Empresa", 985100005, 30);

        return List.of(department1, department2, department3, department4, department5);
    }

    public Department findById(Long id) {
        Department department1 = new Department(1L, "Informatica", "INF", "Escuela de Ingenieria Informatica", 985100001, 40);
        Department department2 = new Department(2L, "Matematicas", "MAT", "Facultad de Ciencias", 985100002, 28);
        Department department3 = new Department(3L, "Fisica", "FIS", "Facultad de Ciencias", 985100003, 22);
        Department department4 = new Department(4L, "Quimica", "QUI", "Facultad de Ciencias", 985100004, 24);
        Department department5 = new Department(5L, "Economia", "ECO", "Facultad de Economia y Empresa", 985100005, 30);

        List<Department> departments = List.of(department1, department2, department3, department4, department5);

        for (Department department : departments) {
            if (department.getId().equals(id)) {
                return department;
            }
        }
        return null;
    }

    public Department saveDepartment(Department department) {
        //TODO: Temporal
        Department newDepartment = department;
        return newDepartment;
    }

    public Department updateDepartment(Department department) {
        //TODO: Temporal
        Department newDepartment = department;
        return newDepartment;
    }

    public Department deleteDepartment(Long id) {
        //TODO Temporal
        Department newDepartment = new Department(id, "Informatica", "INF", "Escuela de Ingenieria Informatica", 985100001, 40);

        return newDepartment;
    }
}
