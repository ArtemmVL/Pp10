package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(ModelMap model) {
        model.addAttribute("user", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/create")
    public String createUserForm(ModelMap model) {
        User user = new User();
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "create";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/update")
    public String updateUserForm(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.findById(id);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "/update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
