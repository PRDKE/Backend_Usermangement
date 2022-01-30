package com.usermanagement.Usermanagement.service;

import com.usermanagement.Usermanagement.exception.badRequest.BadRequestException;
import com.usermanagement.Usermanagement.exception.usernameAlreadyExists.UsernameAlreadyExistsException;
import com.usermanagement.Usermanagement.exception.wrongInput.WrongInputException;
import com.usermanagement.Usermanagement.model.User;
import com.usermanagement.Usermanagement.repository.UserRegistrationRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class UserRegistrationService {

    private final UserRegistrationRepository userRegistrationRepository;
    private final String key = "ölkjaQEklöQökd1354";

    public UserRegistrationService(UserRegistrationRepository userRegistrationRepository) {
        this.userRegistrationRepository = userRegistrationRepository;
    }

    // encrypts the password with the user of Base64
    public String convertToEncrypt(String password) {
        if (password.isEmpty()){
            return null;
        } else {
            password += key;
            return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
        }
    }

    // retrieves all users
    public List<User> findUsers() {
        return userRegistrationRepository.findAll();
    }

    // retrieves a user with the matching username
    public User findUserByUsername(String username) {
        return userRegistrationRepository.findUserByUsername(username);
    }

    // creates a new user
    // this method is called during a registration//
    // throws exception if username already exists
    public User saveUser(User user) throws Exception {
        if(findUserByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("This username is already in use!");
        } else {
            user.setPassword(convertToEncrypt(user.getPassword()));
            return userRegistrationRepository.save(user);
        }
    }

    // tries to fetch a user with the matching username and password
    // throws exception if the username and password as a combination do not exist in the database
    public User fetchUserByUsernameAndPassword(String username, String password) throws Exception {
        if (username == null || password == null) {
            throw new BadRequestException("Bad request!");
        }
        password = convertToEncrypt(password);
        if(userRegistrationRepository.findUserByUsernameAndPassword(username, password) == null) {
            throw new WrongInputException("Wrong username or password!");
        }

        return userRegistrationRepository.findUserByUsernameAndPassword(username, password);
    }

    // updates user information of the user with the given username
    public User changeUserInformation(String username, User user) throws Exception {
        if (userRegistrationRepository.findUserByUsername(user.getUsername()) != null && !username.equals(user.getUsername())) {
            throw new UsernameAlreadyExistsException("This username already exists");
        }
        User currentUser = userRegistrationRepository.findUserByUsername(username);
        currentUser = user;
        return userRegistrationRepository.save(currentUser);
    }
}
