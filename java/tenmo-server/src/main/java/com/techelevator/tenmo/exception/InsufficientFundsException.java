package com.techelevator.tenmo.exception;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
        super("Sorry you don't have that much money. Any transfer needs to be less than your current balance.");
    }

}
