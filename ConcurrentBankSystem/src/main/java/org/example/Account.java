package org.example;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected ReentrantLock lock = new ReentrantLock();

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(double amount) {
        lock.lock();
        try {
            while (balance < amount) { // Wait until sufficient funds are available
                System.out.println("Insufficient funds for withdrawal. Waiting for balance to increase...");
            }
            balance -= amount;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public abstract String getAccountType();

    public String getAccountDetails() {
        lock.lock();
        try {
            return "Account Type: " + getAccountType() + "\nAccount Number: " + accountNumber + "\nBalance: " + balance;
        } finally {
            lock.unlock();
        }
    }
}

