package com.ium.repetitaiuvant.DAO;

public class User {
    private String username;
    private String password;
    private Role role;
    private String name;
    private String surname;

    public User(String username, String password, Role role, String name, String surname) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
    }

    //Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    //Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

}
