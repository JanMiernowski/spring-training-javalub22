package pl.sda.springtrainingjavalub22.domain.rent;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@AllArgsConstructor
public class RentPriceCalculator {

    private RentInfoValidator validator;

    public BigDecimal calculatePrice(RentInfo rentInfo) {
        validator.validateRentInfo(rentInfo);

        long days = calculatePeriodLength(rentInfo);

        BigDecimal changePriceAsPercents = getChangePricePercent(rentInfo, days);
        BigDecimal normalPrice = getNormalPrice(rentInfo.getPriceForDay(), BigDecimal.valueOf(days));

        BigDecimal changePriceSum = normalPrice.multiply(changePriceAsPercents);
        return normalPrice.add(changePriceSum);
    }

    private BigDecimal getChangePricePercent(RentInfo rentInfo, long days) {
        BigDecimal changePriceRate = calculateChangePriceRate(rentInfo, days);
        changePriceRate = normalizeChangePriceRate(changePriceRate);
        return changePriceRate.divide(BigDecimal.valueOf(100));
    }

    private BigDecimal getNormalPrice(BigDecimal priceForDay, BigDecimal bigDecimal) {
        return priceForDay.multiply(bigDecimal);
    }

    private BigDecimal normalizeChangePriceRate(BigDecimal changePriceRate) {
        if (changePriceRate.compareTo(BigDecimal.valueOf(50)) > 0) {
            changePriceRate = BigDecimal.valueOf(50L);
        }
        if (changePriceRate.compareTo(BigDecimal.valueOf(-50)) < 0) {
            changePriceRate = BigDecimal.valueOf(-50L);
        }
        return changePriceRate;
    }

    private BigDecimal calculateChangePriceRate(RentInfo rentInfo, long days) {
        BigDecimal discountSum = calculateDiscountsSum(rentInfo);
        BigDecimal increasesSum = calculateIncreases(rentInfo);

        BigDecimal changePriceRate = increasesSum.subtract(discountSum);
        if (days > 10) {
            changePriceRate = changePriceRate.add(BigDecimal.valueOf(-10L));
        }
        return changePriceRate;
    }

    private BigDecimal calculateIncreases(RentInfo rentInfo) {
        return rentInfo.getIncreases().stream()
                    .map(RentInfo.Increase::getRate)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateDiscountsSum(RentInfo rentInfo) {
        return rentInfo.getDiscounts().stream()
                    .map(RentInfo.Discount::getRate)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private long calculatePeriodLength(RentInfo rentInfo) {
        long days = ChronoUnit.DAYS.between(rentInfo.getFrom(), rentInfo.getTo());
        if (days < 1) {
            throw new IllegalStateException("Period should be at least 1 day");
        }
        return days;
    }

}
