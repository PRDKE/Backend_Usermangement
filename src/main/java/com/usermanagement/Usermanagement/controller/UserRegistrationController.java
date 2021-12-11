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

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> findAllUsers(HttpServletRequest request) {

        // spring check barear token in header 
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null || (!JwtUtils.isJwtTokenValid(jwtToken))) {
            System.err.println("[Usermanagement] No authorization-header set or invalid jwtToken provided.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // print authorizationHeader
        System.out.println("[Usermanagement] JwToken of request: " + jwtToken);


        List<User> userList = userRegistrationService.findUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenModel> loginUser(@RequestBody LoginUser user) throws Exception {
        User fetchedUser = userRegistrationService.fetchUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(fetchedUser == null) {
            throw new Exception("fetchedUser is null.");
        }

        JwtTokenModel jwtToken = new JwtTokenModel(JwtUtils.generateToken(fetchedUser.getUsername(), fetchedUser.getId().toString()));

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws Exception {
        User newUser = userRegistrationService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
