package org.example.chipexchangerbot.tronwallet.converter;

import org.example.chipexchangerbot.tronwallet.dto.TransactionInfo;
import org.example.chipexchangerbot.tronwallet.dto.TransactionsData;
import org.example.chipexchangerbot.tronwallet.model.Transaction;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class TronTransactionsDataToTransactionListConverter implements Converter<TransactionsData, List<Transaction>> {

    @Override
    public List<Transaction> convert(@NotNull TransactionsData transactionsData) {
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionInfo transactionInfo : transactionsData.getData()) {
            BigDecimal value = transactionInfo.getValue().divide(BigDecimal.valueOf(1_000_000), 2, RoundingMode.HALF_UP);
            Instant instant = Instant.ofEpochMilli(transactionInfo.getBlockTimestamp());
            Transaction transaction = new Transaction(transactionInfo.getTransactionId(), transactionInfo.getFrom(),
                    transactionInfo.getTo(), value, instant.atZone(ZoneId.systemDefault()).toLocalDateTime());
            transactions.add(transaction);
        }
        return transactions;
    }
}
