package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.Currency;
import com.khurasia.exchangerateserver.model.CurrencyNotFoundException;
import com.khurasia.exchangerateserver.model.ExchangeRate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RandomExchangeRateProvider implements ExchangeRateProvider {

    private static final long startTime = System.currentTimeMillis();
    private static final long millisInYear = 365 * 24 * 60 * 60 * 1000;
    private static final Random random = new Random();

    private final Map<String, Double> startExchangeRatesToUSD = new ConcurrentHashMap<>();
    private final Map<String, Double> exchangeRateVolatility = new ConcurrentHashMap<>();

    public void setStartExchangeRatesToUSD(Map<String, Double> startExchangeRatesToUSD) {
        this.startExchangeRatesToUSD.putAll(startExchangeRatesToUSD);
    }

    public void setExchangeRateVolatility(Map<String, Double> exchangeRateVolatility) {
        this.exchangeRateVolatility.putAll(exchangeRateVolatility);
    }

    @Override
    public ExchangeRate get(Currency from, Currency to) {
        Double fromToUSD = startExchangeRatesToUSD.get(from.getIsoCode());
        Double toToUSD = startExchangeRatesToUSD.get(to.getIsoCode());
        if (fromToUSD == null) {
            throw new CurrencyNotFoundException(from.getIsoCode());
        }
        if (toToUSD == null) {
            throw new CurrencyNotFoundException(to.getIsoCode());
        }

        double fromVol = exchangeRateVolatility.getOrDefault(from, 1d);
        double toVol = exchangeRateVolatility.getOrDefault(to, 1d);
        long currentTime = System.currentTimeMillis();
        double timeComponent = Math.sqrt(((double) (currentTime - startTime )) / millisInYear);
        double randomFrom = fromToUSD * random.nextGaussian() *  fromVol * timeComponent;
        double randomTo = toToUSD * random.nextGaussian() *  toVol * timeComponent;

        return new ExchangeRate(from, to, currentTime, randomTo/randomFrom);
    }
}
