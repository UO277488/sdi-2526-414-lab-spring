package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Mark;
import com.uniovi.sdi.grademanager.services.MarksService;
import com.uniovi.sdi.grademanager.services.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class MarksController {
    private final MarksService marksService;
    private final UsersService usersService;
    private final HttpSession httpSession;

    public MarksController(MarksService marksService, UsersService usersService, HttpSession httpSession){
        this.marksService = marksService;
        this.usersService = usersService;
        this.httpSession = httpSession;
    }

    @GetMapping(value = "/mark/add")
    public String getMark(Model model) {
        model.addAttribute("mark", new Mark());
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/add";
    }

    @PostMapping(value = "/mark/add")
    public String setMark(@Valid @ModelAttribute("mark") Mark mark, BindingResult result, Model model) {
        if (mark.getUser() == null || mark.getUser().getId() == null) {
            result.rejectValue("user", "mark.validation.user.required");
        } else {
            mark.setUser(usersService.getUser(mark.getUser().getId()));
            if (mark.getUser() == null) {
                result.rejectValue("user", "mark.validation.user.required");
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("usersList", usersService.getUsers());
            return "mark/add";
        }
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }

    @GetMapping("/mark/list")
    public String getList(Model model) {
        Set<Mark> consultedList = (Set<Mark>) (httpSession.getAttribute("consultedList") != null ?
                httpSession.getAttribute("consultedList") : new HashSet<>());
        model.addAttribute("consultedList", consultedList);
        model.addAttribute("marksList", marksService.getMarks());
        return "mark/list";
    }

    @GetMapping("/mark/details/{id}")
    public String getDetails(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }

    @GetMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }

    @PostMapping(value="/mark/edit/{id}")
    public String setEdit(@Valid @ModelAttribute("mark") Mark mark, BindingResult result, @PathVariable Long id, Model model){
        Mark originalMark = marksService.getMark(id);
        if (originalMark == null) {
            return "redirect:/mark/list";
        }
        if (result.hasErrors()) {
            mark.setId(id);
            mark.setUser(originalMark.getUser());
            model.addAttribute("usersList", usersService.getUsers());
            return "mark/edit";
        }
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/"+id;
    }

    @GetMapping("/mark/list/update")
    public String updateList(Model model){
        model.addAttribute("marksList", marksService.getMarks() );
        return "mark/list :: marksTable";
    }
}
