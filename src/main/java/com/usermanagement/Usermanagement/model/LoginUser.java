package com.usermanagement.Usermanagement.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
public class LoginUser {
    @Id
    private String username;
    private String password;

    public LoginUser() {}

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
