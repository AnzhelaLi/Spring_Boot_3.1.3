package org.example.controller;

import org.example.service.RoleService;
import org.example.model.User;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class ViewController {

    private UserServiceImpl userService;

    @Autowired
    public ViewController(UserServiceImpl userService) {

        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public String list(Model model, @AuthenticationPrincipal UserDetails detailUser) {

        User currentUser = (User) userService.loadUserByUsername(detailUser.getUsername());

        model.addAttribute("currentUser", currentUser);

        if ((currentUser.getRoles().size() == 1) & currentUser.getRoles().stream().findFirst().get()
                .getRole().contains("ROLE_USER")) {
            model.addAttribute("roleOfCurrentUser", "ROLE_USER");
        } else {
            model.addAttribute("roleOfCurrentUser", "notUser");
        }
        return "examplePage";
    }
}
