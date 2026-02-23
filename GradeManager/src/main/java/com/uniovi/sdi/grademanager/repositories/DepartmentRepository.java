package com.uniovi.sdi.grademanager.repositories;

import com.uniovi.sdi.grademanager.entities.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    boolean existsByCodeIgnoreCase(String code);
}
