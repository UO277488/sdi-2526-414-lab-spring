package com.uniovi.sdi.grademanager.services;
import com.uniovi.sdi.grademanager.entities.Department;
import com.uniovi.sdi.grademanager.entities.User;
import com.uniovi.sdi.grademanager.repositories.DepartmentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class InsertDataSampleService {
    private final UsersService usersService;
    private final DepartmentRepository departmentRepository;
    public InsertDataSampleService(UsersService usersService, DepartmentRepository departmentRepository) {
        this.usersService = usersService;
        this.departmentRepository = departmentRepository;
    }
    @PostConstruct
    public void init() {
        ensureLoginUser("99999990A", "Pedro", "Díaz");
        ensureLoginUser("99999988F", "Edward", "Núñez");
        ensureDepartment("department.seed.computerScience.name", "COMP0001A", "department.seed.scienceFaculty.name", "985111111", 24);
        ensureDepartment("department.seed.softwareEngineering.name", "SOFT0002B", "department.seed.engineeringFaculty.name", "985222222", 18);
        ensureDepartment("department.seed.math.name", "MATH0003C", "department.seed.scienceFaculty.name", "985333333", 16);
        ensureDepartment("department.seed.electronics.name", "ELEC0004D", "department.seed.engineeringFaculty.name", "985444444", 20);
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

    private void ensureDepartment(String nameKey, String code, String facultyKey, String phone, int professors) {
        if (departmentRepository.existsByCodeIgnoreCase(code)) {
            return;
        }
        Department department = new Department();
        department.setName(nameKey);
        department.setCode(code);
        department.setFaculty(facultyKey);
        department.setPhone(phone);
        department.setProfessors(professors);
        departmentRepository.save(department);
    }
}
