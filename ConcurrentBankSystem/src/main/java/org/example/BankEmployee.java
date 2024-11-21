package org.example;

public class BankEmployee extends User {
    public BankEmployee(String userID, String name) {
        super(userID, name);
    }

    @Override
    public String getRole() {
        return "Bank Employee";
    }

    public void editAccountDetails(Account account, double newBalance) {
        account.lock.lock();
        try {
            account.balance = newBalance;
        } finally {
            account.lock.unlock();
        }
    }
}
