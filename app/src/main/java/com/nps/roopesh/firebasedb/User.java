package com.nps.roopesh.firebasedb;

/**
 * Created by roopesh on 17/3/18.
 */

public class User {

String user_name;
String password;

    public User(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

}

