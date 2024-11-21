package org.example;

public abstract class User {
    protected String userID;
    protected String name;

    public User(String userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    public abstract String getRole();
}

