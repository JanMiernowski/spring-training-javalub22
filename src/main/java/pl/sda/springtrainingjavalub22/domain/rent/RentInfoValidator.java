package pl.sda.springtrainingjavalub22.domain.rent;

import java.math.BigDecimal;

public class RentInfoValidator {

    public void validateRentInfo(RentInfo rentInfo) {
        if (rentInfo.getFrom() == null || rentInfo.getTo() == null) {
            throw new IllegalArgumentException("Dates should be passed");
        }
        if (rentInfo.getPriceForDay().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}
