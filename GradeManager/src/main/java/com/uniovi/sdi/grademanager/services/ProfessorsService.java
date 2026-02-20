package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Professor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProfessorsService {
    private final List<Professor> professors = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public ProfessorsService() {
        addProfessor(new Professor(null, "11111111P", "Ana", "López"));
        addProfessor(new Professor(null, "22222222Q", "Miguel", "Suárez"));
        addProfessor(new Professor(null, "33333333R", "Laura", "Martínez"));
    }

    public List<Professor> getProfessors() {
        return new ArrayList<>(professors);
    }

    public void addProfessor(Professor professor) {
        professor.setId(idGenerator.incrementAndGet());
        professors.add(professor);
    }
}
