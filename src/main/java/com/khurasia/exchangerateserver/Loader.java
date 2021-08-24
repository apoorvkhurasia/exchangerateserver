package com.khurasia.exchangerateserver;

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
    CommandLineRunner initSystem(RandomExchangeRateProvider exchangeRateProvider) {
        return args -> {
            Map<String, Double> usdExchangeRates = new HashMap<>();
            usdExchangeRates.put("GBP", 0.729471);
            usdExchangeRates.put("INR", 74.157467);
            usdExchangeRates.put("AUD", 1.380885);
            usdExchangeRates.put("EUR", 0.851087);
            usdExchangeRates.put("CHF", 0.911549);
            usdExchangeRates.put("CAD", 1.263333);
            usdExchangeRates.put("JPY", 109.520990);

            Map<String, Double> exchangeRateVol = new HashMap<>();
            exchangeRateVol.put("GBP", 5.12);
            exchangeRateVol.put("INR", 1.88);
            exchangeRateVol.put("AUD", 6.08);
            exchangeRateVol.put("EUR", 3.61);
            exchangeRateVol.put("CHF", 5.99);
            exchangeRateVol.put("CAD", 7.76);
            exchangeRateVol.put("JPY", 4.52);

            log.info("Loading dummy data into the repositories.");
            exchangeRateProvider.setStartExchangeRatesToUSD(usdExchangeRates);
            exchangeRateProvider.setExchangeRateVolatility(exchangeRateVol);
            log.info("Loaded dummy data into the repositories successfully.");
        };
    }
}
