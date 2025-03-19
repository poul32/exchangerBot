package org.example.chipexchangerbot.tronwallet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(String transactionId, String addressFrom, String addressTo, BigDecimal value,
                          LocalDateTime date) {
}
