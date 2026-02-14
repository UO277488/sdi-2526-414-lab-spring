package com.uniovi.sdi.grademanager.services;

import com.uniovi.sdi.grademanager.entities.Mark;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarksService {
    private final List<Mark> marks;

    @PostConstruct
    public void init() {
        marks.add(new Mark(1L, "Exercise1", 10.0));
        marks.add(new Mark(2L, "Exercise2", 9.0));
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public Mark getMark(Long id) {
        return marks.stream().filter(mark->mark.getId().equals(id)).findFirst().orElse(null);
    }

    public void addMark(Mark mark) {
        //If id null, asign the last position in the list
        if(mark.getId() == null) {
            mark.setId(marks.getLast().getId()+1);
        }
        marks.add(mark);
    }

    public void removeMark(Long id) {
        marks.removeIf(mark -> mark.getId().equals(id));
    }

    public MarksService() {
        marks = new ArrayList<Mark>();
    }

}
