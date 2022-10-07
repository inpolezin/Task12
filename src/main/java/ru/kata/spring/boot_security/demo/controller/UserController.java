package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getUserPage(Principal principal, Model model) {
        User logUser = userService.findUserByFirstname(principal.getName());
        List<String> logUserRoles = logUser.getRoles().stream()
                .map(Role::getName)
                .map(s -> s.substring(5))
                .collect(Collectors.toList());
        System.out.println(logUserRoles);
        model.addAttribute("logUser", logUser);
        model.addAttribute("logUserRoles", logUserRoles);
        return "users/all";
    }
}
