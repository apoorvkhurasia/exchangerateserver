package com.khurasia.exchangerateserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

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

    @Schema(description = "The currency which to be converted.")
    @JsonGetter("from_ccy")
    public Currency getFromCurrency() {
        return fromCurrency;
    }

    @Schema(description = "The currency to which conversion is to be done.")
    @JsonGetter("to_ccy")
    public Currency getToCurrency() {
        return toCurrency;
    }

    @Schema(description = "The exchange rate from the from_ccy to to_ccy.")
    @JsonGetter("rate")
    public double getRate() {
        return rate;
    }

    @Schema(description = "POSIX timestamp (up to milliseconds) as of which this rate was generated.")
    @JsonGetter("as_of")
    public long getAsOf() {
        return asOf;
    }
}
