package org.example;

public class CheckingAccount extends Account{


    public CheckingAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    @Override
    public String getAccountType() {
        return "CHECKING";
    }
}
