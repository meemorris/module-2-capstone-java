package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.InsufficientFundsException;
import com.techelevator.tenmo.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO {

    private JdbcTemplate jdbcTemplate;
    private AccountDAO senderAccountDAO;
    private AccountDAO receiverAccountDAO;

    public JdbcTransferDAO(JdbcTemplate jdbcTemplate, AccountDAO senderAccountDAO, AccountDAO receiverAccountDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.senderAccountDAO = senderAccountDAO;
        this.receiverAccountDAO = receiverAccountDAO;
    }

    @Override
    public TransferStatusDTO sendBucks(TransferDTO transfer, Long fromUserID) {
        BigDecimal amount = transfer.getTransferAmount();
        BigDecimal senderBalance = senderAccountDAO.getBalance(fromUserID);
        TransferStatusDTO transferStatus = new TransferStatusDTO();

        try {
            if (amount.compareTo(senderBalance) > 0) {
                throw new InsufficientFundsException();
            } else {
                Long toUserID = transfer.getToUserID();
                Long transferID = createTransfer(transfer, fromUserID, amount, toUserID);
                updateSenderBalance(fromUserID, amount);
                updateReceiverBalance(toUserID, amount);
                transferStatus = getTransferStatus(transferID);
            }
        } catch (InsufficientFundsException e) {
                transferStatus.setTransferStatusDesc("Rejected");
        }
            return transferStatus;
    }

    private Transfer getTransfer(Long transferID) {
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, transfer_types.transfer_type_desc, transfer_statuses.transfer_status_desc, account_from," +
                "account_to, amount FROM transfers " +
                "JOIN transfer_statuses ON + transfer_statuses.transfer_status_id = transfers.transfer_status_id " +
                "JOIN transfer_types ON transfer_types.transfer_type_id = transfers.transfer_type_id" +
                "WHERE transfer_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferID);

        while(results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    private TransferStatusDTO getTransferStatus(Long transferID) {
        TransferStatusDTO transferStatus = new TransferStatusDTO();
        String sql = "SELECT transfers.transfer_id, ts.transfer_status_desc " +
                "FROM transfer_statuses ts " +
                "JOIN transfers ON transfers.transfer_status_id = ts.transfer_status_id " +
                "WHERE transfer_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferID);

        while(results.next()) {
            transferStatus = mapRowToTransferStatus(results);
        }
        return transferStatus;
    }

    public List<TransferDetailDTO> getTransferList(User currentUser) {
        List<TransferDetailDTO> transferList = new ArrayList<>();
        String sql = "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount, " +
                "acctFrom.account_id AS from_Acct, acctFrom.user_id AS from_User, acctTo.balance AS current_To_Bal, " +
                "acctTo.account_id AS to_Acct, acctTo.user_id AS to_User, initcap(userFrom.username) AS from_Username, " +
                "initcap(userTo.username) AS to_Username " +
                "FROM transfers t " +
                "JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id " +
                "JOIN accounts acctFrom ON account_from = acctFrom.account_id " +
                "JOIN accounts acctTo ON account_to = acctTo.account_id " +
                "JOIN users userFrom ON userFrom.user_id = acctFrom.user_id " +
                "JOIN users userTo ON userTo.user_id = acctTo.user_id " +
                "WHERE (account_from IN (SELECT account_id FROM accounts WHERE user_id = ?) OR account_to IN " +
                "(SELECT account_id FROM accounts WHERE user_id = ?))";

        Long fromUserID = currentUser.getId();

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, fromUserID, fromUserID);

        while(results.next()) {
            transferList.add(mapRowToTransferList(results));
        }
        return updateTransferList(currentUser, transferList);
    }

    private List<TransferDetailDTO> updateTransferList(User currentUser, List<TransferDetailDTO> transferList) {

        for (TransferDetailDTO transfer : transferList) {
            if (currentUser.getUsername().equalsIgnoreCase(transfer.getFromUsername())) {
                transfer.setDisplayFromOrTo("To: " + transfer.getToUsername());
            } else {
                transfer.setDisplayFromOrTo("From: " + transfer.getFromUsername());
            }
        }
        return transferList;
    }

    private Long createTransfer(TransferDTO transfer, Long fromUserID, BigDecimal amount, Long toUserID) {
        String sql = "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                "VALUES (2, 2, ?, ?, ?) RETURNING transfer_id";
        return jdbcTemplate.queryForObject(sql, Long.class, getCurrentUserAccountID(fromUserID), getToUserAccountID(transfer, toUserID), amount);
    }

    private Long getCurrentUserAccountID(Long fromUserID) {
        String accountIdSql = "SELECT account_id FROM accounts WHERE user_id = ?";
        return jdbcTemplate.queryForObject(accountIdSql, Long.class, fromUserID);
    }

    private Long getToUserAccountID(TransferDTO transfer, Long toUserID) {
        String accountIdSql = "SELECT account_id FROM accounts WHERE user_id = ?";
        return jdbcTemplate.queryForObject(accountIdSql, Long.class, toUserID);
    }

    private void updateSenderBalance(Long fromUserID, BigDecimal amount) {
        BigDecimal senderBalance = senderAccountDAO.getBalance(fromUserID);
        BigDecimal updatedSenderBalance = senderBalance.subtract(amount);
        String moreSql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(moreSql, updatedSenderBalance, fromUserID);
    }

    private void updateReceiverBalance(Long toUserID, BigDecimal amount) {
        BigDecimal receiverBalance = receiverAccountDAO.getBalance(toUserID);
        BigDecimal updateReceiverBalance = receiverBalance.add(amount);
        String moreSql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(moreSql, updateReceiverBalance, toUserID);
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransferID(results.getLong("transfer_id"));
        transfer.setTransferTypeID(results.getLong("transfer_type_id"));
        transfer.setTransferStatusID(results.getLong("transfer_status_id"));
        transfer.setAccountFrom(results.getLong("account_from"));
        transfer.setAccountTo(results.getLong("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }

    private TransferDetailDTO mapRowToTransferList(SqlRowSet results) {
        TransferDetailDTO transferDetail = new TransferDetailDTO();
        transferDetail.setTransferID(results.getLong("transfer_id"));
        transferDetail.setTransferTypeDesc(results.getString("transfer_type_desc"));
        transferDetail.setTransferStatusDesc(results.getString("transfer_status_desc"));
        transferDetail.setAmount(results.getBigDecimal("amount"));
        transferDetail.setFromAcct(results.getLong("from_acct"));
        transferDetail.setFromUserID(results.getLong("from_user"));
        transferDetail.setCurrentToBalance(results.getBigDecimal("current_to_bal"));
        transferDetail.setToAccount(results.getLong("to_acct"));
        transferDetail.setToUserID(results.getLong("to_user"));
        transferDetail.setFromUsername(results.getString("from_username"));
        transferDetail.setToUsername(results.getString("to_username"));
        return transferDetail;
    }

    private TransferStatusDTO mapRowToTransferStatus(SqlRowSet results) {
        TransferStatusDTO transferStatus = new TransferStatusDTO();
        transferStatus.setTransfer_id(results.getLong("transfer_id"));
        transferStatus.setTransferStatusDesc(results.getString("transfer_status_desc"));
        return transferStatus;
    }

}
