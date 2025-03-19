package org.example.chipexchangerbot.tronwallet.dto;

public class TokenInfo {
    private String symbol;
    private String address;
    private int decimals;
    private String name;

    public String getSymbol() {
        return symbol;
    }

    public String getAddress() {
        return address;
    }

    public int getDecimals() {
        return decimals;
    }

    public String getName() {
        return name;
    }
}
