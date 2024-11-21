package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Account account1 = new CheckingAccount("12345", 1000);
        Account account2 = new SavingsAccount("67890", 2000, 0.02);

        bank.createAccount("12345", account1);
        bank.createAccount("67890", account2);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Testing concurrent access
        executor.submit(() -> {
            System.out.println("Checking balance: " + account1.getBalance());
            account1.deposit(500);
            System.out.println("Balance after deposit: " + account1.getBalance());
        });

        executor.submit(() -> {
            boolean success = account1.withdraw(700);
            System.out.println("Withdrawal status: " + (success ? "Successful" : "Failed"));
            System.out.println("Balance after withdrawal: " + account1.getBalance());
        });

        // Transfer funds
        executor.submit(() -> bank.transfer("12345", "67890", 300));

        // Print account details
        executor.submit(() -> bank.printAccountDetails("12345"));
        executor.submit(() -> bank.printAccountDetails("67890"));

        executor.shutdown();
    }
}