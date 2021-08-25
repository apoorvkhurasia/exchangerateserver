package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.Currency;
import com.khurasia.exchangerateserver.model.CurrencyNotFoundException;
import com.khurasia.exchangerateserver.model.ExchangeRate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static com.khurasia.exchangerateserver.model.Currency.USD;

@Component
public class RandomExchangeRateProvider implements ExchangeRateProvider {

    private static final long millisInYear = 365L * 24 * 60 * 60;
    private static final int millisInSecond = 60000;
    private static final Random random = new Random();

    private final Map<Currency, ExchangeRate> startExchangeRatesToUSD = new ConcurrentHashMap<>();
    private final Map<Currency, Double> exchangeRateVolatility = new ConcurrentHashMap<>();

    public void setStartExchangeRatesToUSD(Map<Currency, ExchangeRate> startExchangeRatesToUSD) {
        this.startExchangeRatesToUSD.putAll(startExchangeRatesToUSD);
    }

    public void setExchangeRateVolatility(Map<Currency, Double> exchangeRateVolatility) {
        this.exchangeRateVolatility.putAll(exchangeRateVolatility);
    }

    @Override
    public ExchangeRate get(Currency from, Currency to) {
        long currentTime = System.currentTimeMillis();
        double randomFromRate = getRandomExchangeRateToUSD(from, currentTime);
        double randomToRate = getRandomExchangeRateToUSD(to, currentTime);
        return new ExchangeRate(from, to, currentTime, randomToRate / randomFromRate);
    }

    @Override
    public ExchangeRate awaitAndGetUpdate(Currency from, Currency to, long maxWaitTimeInMillis) {
        long nextTick = Math.min(random.nextInt(millisInSecond), maxWaitTimeInMillis);
        try {
            Thread.sleep(nextTick);
        } catch (InterruptedException iex) {
            Thread.currentThread().interrupt();
        }
        return get(from, to);
    }

    private double getRandomExchangeRateToUSD(Currency of, long time) {
        if ("USD".equals(of.getIsoCode())) {
            return 1;
        } else {
            ExchangeRate updatedRate = startExchangeRatesToUSD.computeIfPresent(of, (ccy, startRate) -> {
                double fromVol = exchangeRateVolatility.getOrDefault(ccy, 1d) / 100d;
                double timeComponent = Math.sqrt(((double) (time - startRate.getAsOf())) / millisInYear);
                double simulatedRate = Math.round(
                        startRate.getRate() * (1 + random.nextGaussian() * fromVol * timeComponent) * 10000d) / 10000d;
                return new ExchangeRate(of, USD, time, simulatedRate);
            });
            if (updatedRate == null) {
                throw new CurrencyNotFoundException(of.getIsoCode());
            }
            return updatedRate.getRate();
        }
    }
}
