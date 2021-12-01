package com.usermanagement.Usermanagement.controller;

import com.usermanagement.Usermanagement.model.User;
import com.usermanagement.Usermanagement.service.UserRegistrationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> userList = userRegistrationService.findUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) throws Exception {
        return new ResponseEntity<>(userRegistrationService.fetchUserByUsernameAndPassword(user.getUsername(), user.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws Exception {
        User newUser = userRegistrationService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
