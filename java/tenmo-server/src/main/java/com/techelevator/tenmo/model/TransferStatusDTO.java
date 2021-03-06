package com.techelevator.tenmo.model;

public class TransferStatusDTO {

    private Long transfer_id;
    private String transferStatusDesc;

    public Long getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(Long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }



}
