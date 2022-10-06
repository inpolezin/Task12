package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    void saveUser(User user, List<Long> roleIds);
    User findUserById(Long id);
    void updateUser(User user, List<Long> roleIds);
    void deleteUserById(Long id);

    User findUserByFirstname(String username);
    void init();

}
