package com.uniovi.sdi.grademanager.repositories;

import com.uniovi.sdi.grademanager.entities.Department;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Department d SET d.name = :name, d.code = :code, d.faculty = :faculty, d.phone = :phone, d.professors = :professors WHERE d.id = :id")
    int updateDepartment(@Param("id") Long id,
                         @Param("name") String name,
                         @Param("code") String code,
                         @Param("faculty") String faculty,
                         @Param("phone") int phone,
                         @Param("professors") int professors);
}
