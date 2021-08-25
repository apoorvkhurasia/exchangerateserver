package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.Currency;
import com.khurasia.exchangerateserver.model.ExchangeRate;

public interface ExchangeRateProvider {

    ExchangeRate get(Currency from, Currency to);

    ExchangeRate awaitAndGetUpdate(Currency from, Currency to, long maxWaitTimeInMillis);

}
