package com.usermanagement.Usermanagement.repository;

import com.usermanagement.Usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserByUsernameAndPassword(String username, String password);
}
