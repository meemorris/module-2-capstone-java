package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/account")
@RestController
//starting path: http://localhost:8080
public class TransferController {

    private AccountDAO accountDAO;
    private UserDAO userDAO;
    private TransferDAO transferDAO;

    public TransferController(AccountDAO accountDAO, UserDAO userDAO, TransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
        this.transferDAO = transferDAO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public TransferStatusDTO send(Principal principal, @Valid @RequestBody TransferDTO transfer) {
        User currentUser = userDAO.findByUsername(principal.getName());
        Long fromUserID = currentUser.getId();
        return transferDAO.sendBucks(transfer, fromUserID);
    }

    @RequestMapping(path = "/transfer/history", method = RequestMethod.GET)
    public List<TransferDetailDTO> viewTransfers(Principal principal) {
        User currentUser = userDAO.findByUsername(principal.getName());
        return transferDAO.getTransferList(currentUser);
    }


}
