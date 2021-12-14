package org.example.controller;

import org.example.model.User;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/rest")
public class SimpleRestController {

    private UserServiceImpl userService;

    @Autowired
    public SimpleRestController(UserServiceImpl userService) {

        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> list() {
        return new ResponseEntity<>(userService.usersList(), HttpStatus.OK);
    }

    @GetMapping("/getAuthorizedUser")
    public ResponseEntity<User> getAuthorizedUser(@AuthenticationPrincipal UserDetails detailUser){
        User currentUser = (User) userService.loadUserByUsername(detailUser.getUsername());
        return new ResponseEntity<>(userService.findUserById(currentUser.getId()), HttpStatus.OK);
    }

    @PutMapping(value ="/edit" )
    public ResponseEntity<User> update(@RequestBody User updatedUser) {
            return new ResponseEntity<>(userService.updateUser(updatedUser, updatedUser.getRoles()), HttpStatus.OK);
  }

    @PostMapping(value="/addNew")
    public ResponseEntity<User> create(@RequestBody User newUser) {
        return new ResponseEntity<>(userService.registerUser(newUser, newUser.getRoles()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
