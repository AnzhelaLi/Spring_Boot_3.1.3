package org.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.example.model.Role;
import org.example.service.RoleService;
import org.example.model.User;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class SimpleRestController {

    private UserServiceImpl userService;
    private RoleService roleService;

    @Autowired
    public SimpleRestController(UserServiceImpl userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value= "/allUsers",  produces = "application/json")
    public ResponseEntity<List<User>> list() {
        List<User> allUsersList = userService.usersList();

        return ResponseEntity.ok(allUsersList);
    }

    @PostMapping("/edit")
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
