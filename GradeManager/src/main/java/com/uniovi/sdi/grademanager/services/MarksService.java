package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Mark;
import com.uniovi.sdi.grademanager.entities.User;
import com.uniovi.sdi.grademanager.repositories.MarksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public Page<Mark> getMarks(Pageable pageable) {
        return marksRepository.findAll(pageable);
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

    public Page<Mark> getMarksForUser(Pageable pageable, User user) {
        Page<Mark> marks = new PageImpl<>(new LinkedList<>());
        if (user == null || user.getRole() == null) {
            return marks;
        }
        if ("ROLE_STUDENT".equals(user.getRole())) {
            marks = marksRepository.findAllByUser(pageable, user);
        }
        if ("ROLE_PROFESSOR".equals(user.getRole()) || "ROLE_ADMIN".equals(user.getRole())) {
            marks = getMarks(pageable);
        }
        return marks;
    }

    public Page<Mark> searchMarksByDescriptionAndNameForUser(Pageable pageable, String searchText, User user) {
        Page<Mark> marks = new PageImpl<>(new LinkedList<>());
        if (user == null || user.getRole() == null) {
            return marks;
        }
        String normalizedSearchText = "%" + searchText + "%";
        if ("ROLE_STUDENT".equals(user.getRole())) {
            marks = marksRepository.searchByDescriptionNameAndUser(pageable, normalizedSearchText, user);
        }
        if ("ROLE_PROFESSOR".equals(user.getRole()) || "ROLE_ADMIN".equals(user.getRole())) {
            marks = marksRepository.searchByDescriptionAndName(pageable, normalizedSearchText);
        }
        return marks;
    }
}
