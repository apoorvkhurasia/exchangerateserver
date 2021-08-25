package com.khurasia.exchangerateserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

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

    @Schema(description = "ISO currency code for this currency.", example = "USD, GBP, etc.")
    @JsonGetter("iso_code")
    public String getIsoCode() {
        return isoCode;
    }

    @Schema(description = "Human friendly name of the currency.")
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
