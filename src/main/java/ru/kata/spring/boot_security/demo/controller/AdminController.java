package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @GetMapping
    public String getAllUsersPage(Principal principal, Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("logUser", userService.findUserByFirstname(principal.getName()));
        return "admins/all";
    }

    @GetMapping("/new")
    public String getCreateUserPage(Principal principal, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("logUser", userService.findUserByFirstname(principal.getName()));
        model.addAttribute("roles", roleService.findAllRoles());
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
    public String getEditUserPage(Principal principal, Model model, @PathVariable("id") Long id) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("logUser", userService.findUserByFirstname(principal.getName()));
        model.addAttribute("roles", roleService.findAllRoles());
        return "admins/edit";
    }
    @GetMapping("/delete/{id}")
    public String getDeleteUserPage(Principal principal, Model model, @PathVariable("id") Long id) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("logUser", userService.findUserByFirstname(principal.getName()));
        model.addAttribute("roles", roleService.findAllRoles());
        return "admins/delete";
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admins";
    }
}