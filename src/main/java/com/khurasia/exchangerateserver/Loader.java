package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.Currency;
import com.khurasia.exchangerateserver.model.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Loader {

    private static final Logger log = LoggerFactory.getLogger(Loader.class);

    @Bean
    CommandLineRunner initSystem(CurrencyProvider currencyProvider, RandomExchangeRateProvider exchangeRateProvider) {
        return args -> {
            Map<String, Double> usdExchangeRatesToCcy = new HashMap<>();
            usdExchangeRatesToCcy.put("GBP", 0.729471);
            usdExchangeRatesToCcy.put("INR", 74.157467);
            usdExchangeRatesToCcy.put("AUD", 1.380885);
            usdExchangeRatesToCcy.put("EUR", 0.851087);
            usdExchangeRatesToCcy.put("CHF", 0.911549);
            usdExchangeRatesToCcy.put("CAD", 1.263333);
            usdExchangeRatesToCcy.put("JPY", 109.520990);

            Map<String, Double> rawCcyCodeAnnualVol = new HashMap<>();
            rawCcyCodeAnnualVol.put("GBP", 5.12);
            rawCcyCodeAnnualVol.put("INR", 1.88);
            rawCcyCodeAnnualVol.put("AUD", 6.08);
            rawCcyCodeAnnualVol.put("EUR", 3.61);
            rawCcyCodeAnnualVol.put("CHF", 5.99);
            rawCcyCodeAnnualVol.put("CAD", 7.76);
            rawCcyCodeAnnualVol.put("JPY", 4.52);

            log.info("Loading dummy data into the repositories.");
            long startTime = System.currentTimeMillis();
            Map<Currency, ExchangeRate> usdExchangeRates = new HashMap<>();
            Map<Currency, Double> ccyAnnualisedVols = new HashMap<>();
            for (Currency ccy : currencyProvider.getAllSupportedCurrencies()) {
                if (!ccy.equals(Currency.USD)) {
                    usdExchangeRates.put(ccy, new ExchangeRate(ccy, Currency.USD, startTime,
                            usdExchangeRatesToCcy.get(ccy.getIsoCode())));
                    ccyAnnualisedVols.put(ccy, rawCcyCodeAnnualVol.get(ccy.getIsoCode()));
                }
            }
            exchangeRateProvider.setStartExchangeRatesToUSD(usdExchangeRates);
            exchangeRateProvider.setExchangeRateVolatility(ccyAnnualisedVols);
            log.info("Loaded dummy data into the repositories successfully.");
        };
    }
}
