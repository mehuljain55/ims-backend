package com.cis.investicationsystem.entity;

import com.cis.investicationsystem.entity.enums.UserRoles;
import jakarta.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
    private String emailId;
    private String name;
    private String password;
    private String mobileNo;
    @Enumerated(EnumType.STRING)
    private UserRoles role;


    public User(String emailId, String name, String mobileNo, UserRoles role) {
        this.emailId = emailId;
        this.name = name;
        this.mobileNo = mobileNo;
        this.role = role;
    }

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
