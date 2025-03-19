package org.example.chipexchangerbot.tronwallet;

import org.example.chipexchangerbot.tronwallet.model.Transaction;

import java.util.List;

public interface Wallet {

    List<Transaction> returnTransactionsByAddress(String address);

    String getPublicAddress();
}
