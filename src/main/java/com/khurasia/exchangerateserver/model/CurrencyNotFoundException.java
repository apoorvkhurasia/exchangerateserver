package com.khurasia.exchangerateserver.model;

public class CurrencyNotFoundException extends RuntimeException {

    private final String isoCurrencyCode;

    public CurrencyNotFoundException(String isoCurrencyCode) {
        super("Currency " + isoCurrencyCode + " not found.");
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public String getIsoCurrencyCode() {
        return isoCurrencyCode;
    }
}
