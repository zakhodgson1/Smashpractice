package com.example.smashpractice;

import android.app.Application;



public class UserInfo extends Application {

    private String email;
    private String userName;
    private String userID;
    private String main;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void clear(){
        setUserID("");
        setEmail("");
        setUserName("");
        setMain("");
    }

}
