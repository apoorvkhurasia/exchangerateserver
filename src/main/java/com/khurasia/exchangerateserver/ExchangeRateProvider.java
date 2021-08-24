package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.Currency;
import com.khurasia.exchangerateserver.model.CurrencyNotFoundException;
import com.khurasia.exchangerateserver.model.ExchangeRate;

public interface ExchangeRateProvider {

    ExchangeRate get(Currency from, Currency to) throws CurrencyNotFoundException;

}
