package com.uniovi.sdi.grademanager.services;

import java.util.*;
import com.uniovi.sdi.grademanager.entities.User;
import com.uniovi.sdi.grademanager.repositories.UsersRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @PostConstruct
    public void init() {
    }
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }
    public User getUser(Long id) {
        return usersRepository.findById(id).orElse(null);
    }
    public void addUser(User user) {
        String rawPassword = user.getPassword();
        if (rawPassword == null || rawPassword.isBlank()) {
            User existingUser = user.getId() != null ? usersRepository.findById(user.getId()).orElse(null) : null;
            if (existingUser != null) {
                user.setPassword(existingUser.getPassword());
            } else {
                user.setPassword(bCryptPasswordEncoder.encode("123456"));
            }
        } else if (!(rawPassword.startsWith("$2a$") || rawPassword.startsWith("$2b$") || rawPassword.startsWith("$2y$"))) {
            user.setPassword(bCryptPasswordEncoder.encode(rawPassword));
        }
        usersRepository.save(user);
    }
    public User getUserByDni(String dni) {
        return usersRepository.findByDni(dni);
    }
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
