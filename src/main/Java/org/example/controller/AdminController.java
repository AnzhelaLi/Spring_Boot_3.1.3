package org.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.example.model.Role;
import org.example.service.RoleService;
import org.example.model.User;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImpl userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/allUsers")
    public String list(Model model, @AuthenticationPrincipal UserDetails detailUser) {

        User currentUser = (User) userService.loadUserByUsername(detailUser.getUsername());

        model.addAttribute("users", userService.usersList());
        model.addAttribute("newUser", new User());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allRoles", roleService.allRoles());

        if ((currentUser.getRoles().size()==1) & currentUser.getRoles().stream().findFirst().get()
                .getRole().contains("ROLE_USER")) {
            model.addAttribute("roleOfCurrentUser", "ROLE_USER");
        } else {
            model.addAttribute("roleOfCurrentUser", "notUser");
        }
        return "commonPage";
    }

    @PostMapping ("/edit")
    public String update( @ModelAttribute("userForEdit") User user, @RequestParam(value ="editRoles") String[] rolesForUpdate )  {

            Set<Role> rolesSetForUpdate = roleService.rolesFromCheckbox(rolesForUpdate);
            userService.updateUser(user, rolesSetForUpdate);
            return "redirect:/admin/allUsers";

    }

    @PostMapping(value="/addNew")
    public String create(@ModelAttribute("newUser") User user, HttpServletRequest request) {

        String[] rolesForRegister = request.getParameterValues("createRoles");
        Set<Role> rolesSetForRegister = roleService.rolesFromSelectForm(rolesForRegister);
        userService.registerUser(user, rolesSetForRegister);
        return "redirect:/admin/allUsers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/allUsers";
    }
}
