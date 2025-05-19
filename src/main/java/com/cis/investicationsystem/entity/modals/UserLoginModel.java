package com.cis.investicationsystem.entity.modals;

import com.cis.investicationsystem.entity.User;

public class UserLoginModel {

    private User user;
    private  String token;

    public UserLoginModel(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserLoginModel() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}