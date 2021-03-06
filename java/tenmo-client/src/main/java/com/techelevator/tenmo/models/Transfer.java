package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfer {

    private Long toUserID;
    private BigDecimal transferAmount;

    public Long getToUserID() {
        return toUserID;
    }

    public void setToUserID(Long toUserID) {
        this.toUserID = toUserID;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

}
