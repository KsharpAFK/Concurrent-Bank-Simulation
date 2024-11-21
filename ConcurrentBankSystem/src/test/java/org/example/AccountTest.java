package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

class AccountTest {
    @Test
    void testDeposit() {
        Account account = new CheckingAccount("12345", 1000);
        account.deposit(500);
        assertEquals(1500, account.getBalance(), "Deposit should add to balance");
    }

    @Test
    void testWithdrawWithSufficientFunds() {
        Account account = new CheckingAccount("12345", 1000);
        assertTrue(account.withdraw(500), "Withdrawal with sufficient funds should succeed");
        assertEquals(500, account.getBalance(), "Balance should reflect withdrawal");
    }

    @Test
    void testWithdrawWithInsufficientFunds() {
        Account account = new CheckingAccount("12345", 1000);

        // Using executor to simulate waiting for sufficient funds
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            assertFalse(account.withdraw(1500), "Withdrawal with insufficient funds should initially fail");
        });

        executor.submit(() -> {
            account.deposit(1000);
            assertTrue(account.getBalance() >= 1500, "Balance should eventually meet withdrawal amount");
        });

        executor.shutdown();
    }

    @Test
    void testConcurrentWithdrawals() {
        Account account = new CheckingAccount("12345", 1000);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> assertTrue(account.withdraw(500), "First withdrawal should succeed"));
        executor.submit(() -> assertFalse(account.withdraw(600), "Second withdrawal should fail due to insufficient funds"));

        executor.shutdown();
    }
}
