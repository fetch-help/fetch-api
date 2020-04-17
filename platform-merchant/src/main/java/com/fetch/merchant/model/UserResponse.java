package com.fetch.merchant.model;

import java.io.Serializable;

public class UserResponse implements Serializable {

    Long userId;
    String password;
    String userName;

    public UserResponse(Long userId, String userName, String password){
        this.userId = userId;
        this.userName =userName;
        this.password = password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
