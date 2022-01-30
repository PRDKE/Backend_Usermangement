package com.usermanagement.Usermanagement.controller;

import com.usermanagement.Usermanagement.model.JwtTokenModel;
import com.usermanagement.Usermanagement.model.LoginUser;
import com.usermanagement.Usermanagement.model.User;
import com.usermanagement.Usermanagement.service.UserRegistrationService;
import com.usermanagement.Usermanagement.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    // retrieves the user with the matching username
    @GetMapping("/{username}")
    public ResponseEntity<User> findUserByUsername(HttpServletRequest request, @PathVariable String username) {
        // spring check barear token in header
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userRegistrationService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> findLoggedInUser(HttpServletRequest request) {
        // spring check barear token in header
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = JwtUtils.getUsernameFromJwtToken(jwtToken);
        User user = userRegistrationService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // retrieves all registered users
    // this method is not used in the frontend, it's only for test purposes
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> findAllUsers(HttpServletRequest request) {

        // spring check barear token in header 
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // print authorizationHeader
        System.out.println("JwToken of request: " + jwtToken);

        System.out.println(JwtUtils.getUsernameFromJwtToken(jwtToken));
        List<User> userList = userRegistrationService.findUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // login of a user with username and password
    @PostMapping("/login")
    public ResponseEntity<JwtTokenModel> loginUser(@RequestBody LoginUser user) throws Exception {
        User fetchedUser = userRegistrationService.fetchUserByUsernameAndPassword(user.getUsername(), user.getPassword());

        JwtTokenModel jwtToken = new JwtTokenModel(JwtUtils.generateToken(fetchedUser.getUsername(), fetchedUser.getId().toString()));

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    // registers a user
    @PostMapping("/new")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws Exception {
        User newUser = userRegistrationService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // changes the user information
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(HttpServletRequest request, @RequestBody User user) throws Exception {
        // spring check barear token in header
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = JwtUtils.getUsernameFromJwtToken(jwtToken);
        User updatedUser = userRegistrationService.changeUserInformation(username, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
