package org.example;


public class BusinessAccount extends Account {
    private double transactionLimit;

    public BusinessAccount(String accountNumber, double initialBalance, double transactionLimit) {
        super(accountNumber, initialBalance);
        this.transactionLimit = transactionLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > transactionLimit) {
            System.out.println("Transaction exceeds the limit for business accounts.");
            return false;
        }
        return super.withdraw(amount);
    }

    @Override
    public String getAccountType() {
        return "Business";
    }
}
