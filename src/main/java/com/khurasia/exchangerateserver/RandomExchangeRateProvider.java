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
    private static final long millisInYear = 365L * 24 * 60 * 60;
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
        long currentTime = System.currentTimeMillis();
        double randomFromRate = getRandomExchangeRateToUSD(from, currentTime);
        double randomToRate = getRandomExchangeRateToUSD(to, currentTime);
        return new ExchangeRate(from, to, currentTime, randomToRate/randomFromRate);
    }

    private double getRandomExchangeRateToUSD(Currency of, long time) {
        if ("USD".equals(of.getIsoCode())) {
            return 1;
        } else {
            Double startRate = startExchangeRatesToUSD.get(of.getIsoCode());
            if (startRate == null) {
                throw new CurrencyNotFoundException(of.getIsoCode());
            }
            double fromVol = exchangeRateVolatility.getOrDefault(of.getIsoCode(), 1d) / 100d;
            double timeComponent = Math.sqrt(((double) (time - startTime )) / millisInYear);
            double simulatedRate = startRate * ( 1 + random.nextGaussian() *  fromVol * timeComponent);
            return Math.round(simulatedRate * 10000d) / 10000d;
        }
    }
}
