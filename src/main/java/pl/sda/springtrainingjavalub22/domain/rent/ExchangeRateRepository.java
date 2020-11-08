package pl.sda.springtrainingjavalub22.domain.rent;

import java.math.BigDecimal;

public class ExchangeRateRepository {
    public BigDecimal getExchangeRate(String currency) {
        return BigDecimal.valueOf(0.25);
    }
}
