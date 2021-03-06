package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDetailDTO {

    private Long transferID;
    private String transferTypeDesc;
    private String transferStatusDesc;
    private BigDecimal amount;
    private Long fromAcct;
    private Long fromUserID;
    private String fromUsername;
    private BigDecimal currentToBalance;
    private Long toAccount;
    private Long toUserID;
    private String toUsername;
    private String displayFromOrTo;


    public Long getTransferID() {
        return transferID;
    }

    public void setTransferID(Long transferID) {
        this.transferID = transferID;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getFromAcct() {
        return fromAcct;
    }

    public void setFromAcct(Long fromAcct) {
        this.fromAcct = fromAcct;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public void setToAccount(Long toAccount) {
        this.toAccount = toAccount;
    }

    public Long getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(Long fromUserID) {
        this.fromUserID = fromUserID;
    }

    public Long getToUserID() {
        return toUserID;
    }

    public void setToUserID(Long toUserID) {
        this.toUserID = toUserID;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public BigDecimal getCurrentToBalance() {
        return currentToBalance;
    }

    public void setCurrentToBalance(BigDecimal currentToBalance) {
        this.currentToBalance = currentToBalance;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getDisplayFromOrTo() {
        return displayFromOrTo;
    }

    public void setDisplayFromOrTo(String displayFromOrTo) {
        this.displayFromOrTo = displayFromOrTo;
    }
}
