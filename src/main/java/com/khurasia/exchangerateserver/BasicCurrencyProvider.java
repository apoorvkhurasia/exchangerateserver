package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.Currency;
import com.khurasia.exchangerateserver.model.CurrencyNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BasicCurrencyProvider implements CurrencyProvider {

    private final Map<String, Currency> supportedCurrencies = new ConcurrentHashMap<>();

    public BasicCurrencyProvider() {
        supportedCurrencies.put("USD", Currency.USD);
        supportedCurrencies.put("AUD", new Currency("AUD", "Australian Dollar"));
        supportedCurrencies.put("EUR", new Currency("EUR", "Euro"));
        supportedCurrencies.put("JPY", new Currency("JPY", "Japanese Yen"));
        supportedCurrencies.put("GBP", new Currency("GBP", "British Pound Sterling"));
        supportedCurrencies.put("INR", new Currency("INR", "Indian Rupee"));
        supportedCurrencies.put("CHF", new Currency("CHF", "Swiss Frank"));
        supportedCurrencies.put("CAD", new Currency("CAD", "Canadian Dollar"));
    }

    @Override
    public Currency get(String isoCurrencyCode) {
        Currency ccy = supportedCurrencies.get(isoCurrencyCode);
        if (ccy == null) {
            throw new CurrencyNotFoundException(isoCurrencyCode);
        }
        return ccy;
    }

    @Override
    public Collection<Currency> getAllSupportedCurrencies() {
        return Collections.unmodifiableCollection(supportedCurrencies.values());
    }
}

