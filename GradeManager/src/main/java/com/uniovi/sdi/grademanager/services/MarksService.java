package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Mark;
import com.uniovi.sdi.grademanager.entities.User;
import com.uniovi.sdi.grademanager.repositories.MarksRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarksService {
    private final MarksRepository marksRepository;

    public MarksService(MarksRepository marksRepository) {
        this.marksRepository = marksRepository;
    }

    public List<Mark> getMarks() {
        List<Mark> marks = new ArrayList<>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
    }

    public Mark getMark(Long id) {
        return marksRepository.findById(id).orElse(new Mark());
    }

    public void addMark(Mark mark) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        marksRepository.save(mark);
    }

    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
    }

    public void setMarkResend(boolean resend, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();

        Mark mark = marksRepository.findById(id).orElse(null);
        if (mark != null && mark.getUser() != null && dni.equals(mark.getUser().getDni())) {
            marksRepository.updateResend(resend, id);
        }
    }

    public List<Mark> getMarksForUser(User user) {
        if (user == null || user.getRole() == null) {
            return new ArrayList<>();
        }
        if ("ROLE_STUDENT".equals(user.getRole())) {
            return marksRepository.findAllByUser(user);
        }
        if ("ROLE_PROFESSOR".equals(user.getRole()) || "ROLE_ADMIN".equals(user.getRole())) {
            return getMarks();
        }
        return new ArrayList<>();
    }

    public List<Mark> searchMarksByDescriptionAndNameForUser(String searchText, User user) {
        if (user == null || user.getRole() == null) {
            return new ArrayList<>();
        }
        String normalizedSearchText = "%" + searchText + "%";
        if ("ROLE_STUDENT".equals(user.getRole())) {
            return marksRepository.searchByDescriptionNameAndUser(normalizedSearchText, user);
        }
        if ("ROLE_PROFESSOR".equals(user.getRole()) || "ROLE_ADMIN".equals(user.getRole())) {
            return marksRepository.searchByDescriptionAndName(normalizedSearchText);
        }
        return new ArrayList<>();
    }
}
