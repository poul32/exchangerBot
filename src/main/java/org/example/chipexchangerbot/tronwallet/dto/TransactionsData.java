package org.example.chipexchangerbot.tronwallet.dto;

import java.util.List;

public class TransactionsData {
    private List<TransactionInfo> data;
    private boolean success;
    private Meta meta;

    public List<TransactionInfo> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public Meta getMeta() {
        return meta;
    }
}
