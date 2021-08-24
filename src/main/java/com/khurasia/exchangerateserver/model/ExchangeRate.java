package com.khurasia.exchangerateserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ExchangeRate {

    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final long asOf;
    private final double rate;

    @JsonCreator
    public ExchangeRate(@JsonProperty("from_ccy") Currency fromCurrency,
                        @JsonProperty("to_ccy") Currency toCurrency,
                        @JsonProperty("as_of") long asOf,
                        @JsonProperty("rate") double rate) {
        Objects.requireNonNull(fromCurrency);
        Objects.requireNonNull(toCurrency);
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.asOf = asOf;
        this.rate = rate;
    }

    @JsonGetter("from_ccy")
    public Currency getFromCurrency() {
        return fromCurrency;
    }

    @JsonGetter("to_ccy")
    public Currency getToCurrency() {
        return toCurrency;
    }

    @JsonGetter("rate")
    public double getRate() {
        return rate;
    }

    @JsonGetter("as_of")
    public long getAsOf() {
        return asOf;
    }
}
