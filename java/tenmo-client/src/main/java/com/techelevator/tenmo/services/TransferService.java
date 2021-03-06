package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferDetail;
import com.techelevator.tenmo.models.TransferStatus;
import org.apiguardian.api.API;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {

    public static String AUTH_TOKEN = "";
    private final String API_BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public TransferService(String url) {
        API_BASE_URL = url;
    }


    public TransferStatus sendBucks(String token, Long toUserID, BigDecimal transferAmount) {
        Transfer newTransfer = createNewTransfer(toUserID, transferAmount);
        return restTemplate.exchange(API_BASE_URL + "account/transfer", HttpMethod.POST,
                makeTransferEntity(newTransfer, token), TransferStatus.class).getBody();
    }

    public TransferDetail[] viewTransferHistory(String token) {
        return restTemplate.exchange(API_BASE_URL + "account/transfer/history", HttpMethod.GET,
                makeAuthEntity(token), TransferDetail[].class).getBody();
    }

    private Transfer createNewTransfer(Long toUserID, BigDecimal transferAmount) {
        Transfer newTransfer = new Transfer();
        newTransfer.setToUserID(toUserID);
        newTransfer.setTransferAmount(transferAmount);
        return newTransfer;
    }

    private HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }


}
