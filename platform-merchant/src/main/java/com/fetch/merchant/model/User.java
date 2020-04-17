package com.fetch.merchant.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public class User implements Serializable{

    private static final long serialVersionUID = 2396501168768746670L;

    String id;
    String username;
    char[] password;

    @JsonCreator
    public User(@JsonProperty("id") final String id,
         @JsonProperty("username") final String username,
         @JsonProperty("password") final String password) {
        super();
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password).toCharArray();
    }

    @JsonIgnore
    public char[] getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password.toCharArray();
    }

}
