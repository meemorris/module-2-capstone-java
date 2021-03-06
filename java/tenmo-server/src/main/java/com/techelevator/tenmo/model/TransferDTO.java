package com.techelevator.tenmo.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferDTO {

    @NotNull(message = "The 'user id' field should not be empty.")
    private Long toUserID;
    @NotNull(message = "The 'amount' field should not be empty.")
    @Positive(message = "The 'amount' field cannot be negative.")
    private BigDecimal transferAmount;

    public long getToUserID() {
        return toUserID;
    }

    public void setToUserID(long toUserID) {
        this.toUserID = toUserID;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }


}
