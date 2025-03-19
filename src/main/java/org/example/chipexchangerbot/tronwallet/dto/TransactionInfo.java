package org.example.chipexchangerbot.tronwallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransactionInfo {
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("token_info")
    private TokenInfo tokenInfo;
    @JsonProperty("block_timestamp")
    private long blockTimestamp;
    private String from;
    private String to;
    private String type;
    private BigDecimal value;

    public String getTransactionId() {
        return transactionId;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public long getBlockTimestamp() {
        return blockTimestamp;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }
}
