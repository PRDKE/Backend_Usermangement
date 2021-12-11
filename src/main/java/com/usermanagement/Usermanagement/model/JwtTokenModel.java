package com.usermanagement.Usermanagement.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
public class JwtTokenModel {
    @Id
    private String jwtToken;

    public JwtTokenModel() {}

    public JwtTokenModel(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}