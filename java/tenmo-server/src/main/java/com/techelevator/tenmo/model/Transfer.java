package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private Long transferID;
    private Long transferTypeID;
    private Long transferStatusID;
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal amount;

    public Long getTransferID() {
        return transferID;
    }

    public void setTransferID(Long transferID) {
        this.transferID = transferID;
    }

    public Long getTransferTypeID() {
        return transferTypeID;
    }

    public void setTransferTypeID(Long transferTypeID) {
        this.transferTypeID = transferTypeID;
    }

    public Long getTransferStatusID() {
        return transferStatusID;
    }

    public void setTransferStatusID(Long transferStatusID) {
        this.transferStatusID = transferStatusID;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }



}
