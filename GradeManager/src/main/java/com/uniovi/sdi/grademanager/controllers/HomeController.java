package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Mark;
import com.uniovi.sdi.grademanager.entities.User;
import com.uniovi.sdi.grademanager.services.MarksService;
import com.uniovi.sdi.grademanager.services.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final MarksService marksService;
    private final UsersService usersService;

    public HomeController(MarksService marksService, UsersService usersService) {
        this.marksService = marksService;
        this.usersService = usersService;
    }

    @RequestMapping(value = {"/home", "/"})
    public String home(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName();
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = marksService.getMarksForUser(pageable, user);
        model.addAttribute("marksList", marks.getContent());
        model.addAttribute("page", marks);
        return "home";
    }
}
