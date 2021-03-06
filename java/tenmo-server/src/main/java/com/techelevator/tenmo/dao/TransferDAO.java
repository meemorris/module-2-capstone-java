package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.*;

import java.security.Principal;
import java.util.List;

public interface TransferDAO {

    TransferStatusDTO sendBucks(TransferDTO transfer, Long fromUserID);
    List<TransferDetailDTO> getTransferList(User currentUser);
}
