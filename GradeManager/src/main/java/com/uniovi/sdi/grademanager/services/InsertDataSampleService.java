package com.uniovi.sdi.grademanager.services;
import com.uniovi.sdi.grademanager.entities.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class InsertDataSampleService {
    private final UsersService usersService;
    public InsertDataSampleService(UsersService usersService) {
        this.usersService = usersService;
    }
    @PostConstruct
    public void init() {
        ensureLoginUser("99999990A", "Pedro", "Díaz");
        ensureLoginUser("99999988F", "Edward", "Núñez");
    }

    private void ensureLoginUser(String dni, String name, String lastName) {
        User existingUser = usersService.getUserByDni(dni);
        User user;
        if (existingUser != null) {
            user = existingUser;
            user.setName(name);
            user.setLastName(lastName);
        } else {
            user = new User(dni, name, lastName);
        }
        user.setRole("ROLE_STUDENT");
        user.setPassword("123456");
        usersService.addUser(user);
    }
}
