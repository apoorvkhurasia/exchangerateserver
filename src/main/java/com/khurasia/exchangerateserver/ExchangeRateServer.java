package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.Currency;
import com.khurasia.exchangerateserver.model.ExchangeRate;
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

    @GetMapping("/simulated_rate/{from}/{to}")
    public ExchangeRate getExchangeRate(@PathVariable("from") String fromCurrencyCode,
                                        @PathVariable("to") String toCurrencyCode) {
        return exchangeRateProvider.get(
                currencyProvider.get(fromCurrencyCode),
                currencyProvider.get(toCurrencyCode));
    }

    @GetMapping("/all_currencies")
    public List<Currency> getAllSupportedCurrencies() {
        return new ArrayList<>(currencyProvider.getAllSupportedCurrencies());
    }

    @GetMapping("/simulated_rate/{from}")
    public List<ExchangeRate> getExchangeRateAgainstAllSupportedCurrencies(@PathVariable("from") String fromCcyCode) {
        Currency from = currencyProvider.get(fromCcyCode);
        return currencyProvider.getAllSupportedCurrencies()
            .stream()
            .filter(c -> !c.getIsoCode().equals(fromCcyCode))
            .map(c -> exchangeRateProvider.get(from, c))
            .collect(Collectors.toList());
    }
}
