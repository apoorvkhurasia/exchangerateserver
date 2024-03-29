package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.Currency;
import com.khurasia.exchangerateserver.model.ExchangeRate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExchangeRateServer {

    private final CurrencyProvider currencyProvider;
    private final ExchangeRateProvider exchangeRateProvider;

    public ExchangeRateServer(CurrencyProvider currencyProvider, ExchangeRateProvider exchangeRateProvider) {
        this.currencyProvider = currencyProvider;
        this.exchangeRateProvider = exchangeRateProvider;
    }

    /**
     * Gets a list of all supported currencies.
     */
    @Operation(summary = "Gets a list of all supported currencies and their ISO codes.")
    @GetMapping("/all_currencies")
    public List<Currency> getAllSupportedCurrencies() {
        return new ArrayList<>(currencyProvider.getAllSupportedCurrencies());
    }

    /**
     * Gets the exchange rate from the given currency to the given currency.
     * @param fromCurrencyCode The from currency's ISO currency code.
     * @param toCurrencyCode The to currency's ISO currency code.
     * @return The exchange rate.
     */
    @Operation(summary = "Gets a simulated exchange rate between two currencies.")
    @GetMapping("/simulated_rate/{from}/{to}")
    public ExchangeRate getExchangeRate(
            @Parameter(description = "ISO currency code of the currency which is to be converted.")
            @PathVariable("from") String fromCurrencyCode,
            @Parameter(description = "ISO currency code of the currency to which the from currency is to be converted.")
            @PathVariable("to") String toCurrencyCode) {
        return exchangeRateProvider.get(
                currencyProvider.get(fromCurrencyCode),
                currencyProvider.get(toCurrencyCode));
    }

    @Operation(summary = "Gets the exchange rate on the next update or at the timeout, whichever is earliest.")
    @GetMapping("/simulated_rate/await/{from}/{to}/{timeout_in_millis}")
    public ExchangeRate getExchangeRateOnNextUpdate(
            @Parameter(description = "ISO currency code of the currency which is to be converted.")
            @PathVariable("from") String fromCurrencyCode,
            @Parameter(description = "ISO currency code of the currency to which the from currency is to be converted.")
            @PathVariable("to") String toCurrencyCode,
            @Parameter(description = "Timeout in milliseconds") @PathVariable("timeout_in_millis") long millisToWait) {
        return exchangeRateProvider.awaitAndGetUpdate(
                currencyProvider.get(fromCurrencyCode),
                currencyProvider.get(toCurrencyCode),
                millisToWait);
    }

    /**
     * Gets the exchange rate from the given currency to all supported currencies.
     *
     * @param fromCcyCode The from currency's ISO currency code.
     * @return The exchange rates.
     */
    @Operation(summary = "Gets simulated exchange rates against all supported currencies.")
    @GetMapping("/simulated_rate/{from}")
    public List<ExchangeRate> getExchangeRateAgainstAllSupportedCurrencies(
            @Parameter(description = "ISO currency code of the currency which is to be converted.")
            @PathVariable("from") String fromCcyCode) {
        Currency from = currencyProvider.get(fromCcyCode);
        return currencyProvider.getAllSupportedCurrencies()
            .stream()
            .filter(c -> !c.getIsoCode().equals(fromCcyCode))
            .map(c -> exchangeRateProvider.get(from, c))
            .collect(Collectors.toList());
    }
}
