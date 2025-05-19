package com.jktechproject.documentmanagement.dto;

public class UserLogin {



    private String userName;

    public UserLogin(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsserName() {
        return userName;
    }

    public void setUsserName(String usserName) {
        this.userName = usserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;



}
