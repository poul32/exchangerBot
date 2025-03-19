package org.example.chipexchangerbot.tronwallet;

import org.example.chipexchangerbot.tronwallet.converter.TronTransactionsDataToTransactionListConverter;
import org.example.chipexchangerbot.tronwallet.dto.TransactionsData;
import org.example.chipexchangerbot.tronwallet.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Service
public class TronWallet implements Wallet {
    private static final String NILE_TRON_BASE_URL = "https://nile.trongrid.io";
    private final RestClient restClient = RestClient.create();
    private final TronTransactionsDataToTransactionListConverter converter;

    public TronWallet(TronTransactionsDataToTransactionListConverter converter) {
        this.converter = converter;
    }

    @Override
    public List<Transaction> returnTransactionsByAddress(String address) {
        return converter.convert(Objects.requireNonNull(restClient.get()
                .uri(NILE_TRON_BASE_URL + "/v1/accounts/{address}/transactions/trc20?contract_address=TXYZopYRdj2D9XRtbG411XZZ3kM5VkAeBf", address)
                .retrieve()
                .body(TransactionsData.class)));
    }

    @Override
    public String getPublicAddress() {
        return "TLvGqh8DXy8bSmBqNuEnAemBL4J5VbHSY8";
    }
}
