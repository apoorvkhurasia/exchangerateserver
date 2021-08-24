package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.Currency;

import java.util.Collection;

public interface CurrencyProvider {

    Currency get(String isoCurrencyCode);

    Collection<Currency> getAllSupportedCurrencies();
}
