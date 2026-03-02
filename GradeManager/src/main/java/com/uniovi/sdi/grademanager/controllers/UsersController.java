package com.uniovi.sdi.grademanager.controllers;
import com.uniovi.sdi.grademanager.entities.User;
import com.uniovi.sdi.grademanager.services.RolesService;
import com.uniovi.sdi.grademanager.services.SecurityService;
import com.uniovi.sdi.grademanager.services.UsersService;
import com.uniovi.sdi.grademanager.validators.SignUpFormValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
@Controller
public class UsersController {
    private final UsersService usersService;
    private final SecurityService securityService;
    private final SignUpFormValidator signUpFormValidator;
    private final RolesService rolesService;

    public UsersController(UsersService usersService, SecurityService securityService, SignUpFormValidator signUpFormValidator, RolesService rolesService) {
        this.usersService = usersService;
        this.securityService = securityService;
        this.signUpFormValidator = signUpFormValidator;
        this.rolesService = rolesService;
    }

    @GetMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    @GetMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("usersList", usersService.getUsers());
        return "user/add";
    }

    @PostMapping(value = "/user/add")
    public String setUser(@ModelAttribute User user) {
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole(rolesService.getRoles()[0]);
        }
        usersService.addUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }

    @GetMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping(value = "/user/edit/{id}")
    public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
        usersService.updateUserProfile(id, user);
        return "redirect:/user/details/" + id;
    }

    @GetMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("usersList", usersService.getUsers());
        return "fragments/usersTable :: usersTable";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        model.addAttribute("marksList", activeUser != null ? activeUser.getMarks() : Collections.emptySet());
        return "home";
    }
}
