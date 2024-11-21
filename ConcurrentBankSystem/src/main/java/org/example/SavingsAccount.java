package org.example;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, double initialBalance, double interestRate) {
        super(accountNumber, initialBalance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        lock.lock();
        try {
            balance += balance * interestRate;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }
}
