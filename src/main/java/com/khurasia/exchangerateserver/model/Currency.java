package com.khurasia.exchangerateserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Currency {

    private final String isoCode;
    private final String currencyName;

    @JsonCreator
    public Currency(@JsonProperty("iso_code") String isoCode, @JsonProperty("name") String currencyName) {
        Objects.requireNonNull(isoCode, currencyName);
        this.isoCode = isoCode;
        this.currencyName = currencyName;
    }

    @JsonGetter("iso_code")
    public String getIsoCode() {
        return isoCode;
    }

    @JsonGetter("name")
    public String getCurrencyName() {
        return currencyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return isoCode.equals(currency.isoCode) && currencyName.equals(currency.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isoCode, currencyName);
    }
}
