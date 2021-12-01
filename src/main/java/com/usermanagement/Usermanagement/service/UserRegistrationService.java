package com.usermanagement.Usermanagement.service;

import com.usermanagement.Usermanagement.model.User;
import com.usermanagement.Usermanagement.repository.UserRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRegistrationRepository userRegistrationRepository;

    public List<User> findUsers() {
        return userRegistrationRepository.findAll();
    }

    public User findUserByUsername(String username) {
        return userRegistrationRepository.findUserByUsername(username);
    }

    public User saveUser(User user) throws Exception {
        if(findUserByUsername(user.getUsername()) != null) {
            throw new Exception("User with the following username already exists: " + user.getUsername());
        } else if (findUserByUsername(user.getUsername()) != null) {
            throw new Exception("This username is already used!");
        } else {
            return userRegistrationRepository.save(user);
        }
    }

    public User fetchUserByUsernameAndPassword(String username, String password) throws Exception {
        if (username == null || password == null) {
            throw new Exception("Bad request!");
        }
        if(userRegistrationRepository.findUserByUsernameAndPassword(username, password) == null) {
            throw new Exception("Wrong username or password!");
        }
        return userRegistrationRepository.findUserByUsernameAndPassword(username, password);
    }
}
