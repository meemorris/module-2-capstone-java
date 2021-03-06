package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private long accountID;
    private int userID;
    private BigDecimal balance;

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account() {

    }

    public Account(long accountID, int userID, BigDecimal balance) {
        this.accountID = accountID;
        this.userID = userID;
        this.balance = balance;
    }




}
