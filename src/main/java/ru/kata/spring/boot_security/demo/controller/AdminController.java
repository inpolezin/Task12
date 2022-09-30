package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public String getAllUsersPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admins/all";
    }

    @GetMapping("/new")
    public String getCreateUserPage(Model model) {
        model.addAttribute("user", new User());
        return "admins/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admins";
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admins";
    }

    @GetMapping("/edit/{id}")
    public String getEditUserPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        return "admins/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admins";
    }
}