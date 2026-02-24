package com.uniovi.sdi.grademanager.repositories;

import com.uniovi.sdi.grademanager.entities.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorsRepository extends CrudRepository<Professor, Long> {
    Professor findByDni(String dni);
}
