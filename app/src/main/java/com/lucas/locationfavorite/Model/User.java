package com.lucas.locationfavorite.Model;


public class User {
    private String fullname;
    private String password;
    private String email;

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public User(String fullname, String password, String email) {
        this.fullname = fullname;
        this.password = password;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
