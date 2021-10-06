package org.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.example.model.Role;
import org.example.service.RoleService;
import org.example.model.User;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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

    @GetMapping("/allUsers")//полный список
    public String list(Model model, @AuthenticationPrincipal UserDetails detailUser) {


        User currentUser = (User)userService.loadUserByUsername(detailUser.getUsername());


        model.addAttribute("users", userService.usersList());
        model.addAttribute("newUser", new User());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allRoles", roleService.allRoles());


        return "commonPage";
    }

   /*  @GetMapping("/{id}/edit")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",userService.findUserById(id));
          System.out.println(userService.findUserById(id).getName());
          return "user";
    }*/


    @PostMapping ("/edit")
    public String update( @ModelAttribute("userForEdit") User user, @RequestParam(value ="editRoles") String[] rolesForUpdate )  {

            //String[] rolesForUpdate = request.getParameterValues("editRoles");// принимает данные с чекбокса (все - отмеченные и нет)
            Set<Role> rolesSetForUpdate = roleService.rolesFromCheckbox(rolesForUpdate);
            userService.updateUser(user, rolesSetForUpdate);
            return "redirect:/admin/allUsers";

    }

    @PostMapping(value="/addNew")//перенаправление на страницу всех юзеров
    public String create(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/allUsers";
        }

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
