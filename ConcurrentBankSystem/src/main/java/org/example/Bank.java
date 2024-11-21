package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts = new ConcurrentHashMap<>();

    public void createAccount(String accountNumber, Account account) {
        accounts.put(accountNumber, account);
        System.out.println("Account created: " + account.getAccountType() + " - " + accountNumber);
    }

    public void deleteAccount(String accountNumber) {
        accounts.remove(accountNumber);
        System.out.println("Account deleted: " + accountNumber);
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        Account source = accounts.get(fromAccount);
        Account target = accounts.get(toAccount);

        if (source == null || target == null) {
            System.out.println("One or both accounts do not exist.");
            return;
        }

        // Lock ordering to avoid deadlock
        if (fromAccount.compareTo(toAccount) < 0) {
            source.lock.lock();
            target.lock.lock();
        } else {
            target.lock.lock();
            source.lock.lock();
        }

        try {
            if (source.withdraw(amount)) {
                target.deposit(amount);
                System.out.println("Transfer of " + amount + " from " + fromAccount + " to " + toAccount + " completed.");
            } else {
                System.out.println("Transfer failed due to insufficient funds.");
            }
        } finally {
            source.lock.unlock();
            target.lock.unlock();
        }
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void printAccountDetails(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.println(account.getAccountDetails());
        } else {
            System.out.println("Account not found: " + accountNumber);
        }
    }
}

