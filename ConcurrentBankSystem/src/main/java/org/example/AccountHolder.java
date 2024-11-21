package org.example;

public class AccountHolder extends User {
    public AccountHolder(String userID, String name) {
        super(userID, name);
    }

    @Override
    public String getRole() {
        return "Account Holder";
    }
}
