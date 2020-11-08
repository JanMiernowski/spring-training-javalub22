package pl.sda.springtrainingjavalub22.domain.rent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentInfo {
    private LocalDate from;
    private LocalDate to;
    private BigDecimal priceForDay;
    private Set<Discount> discounts;
    private Set<Increase> increases;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Discount {
        private String name;
        private BigDecimal rate;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Increase {
        private String reason;
        private BigDecimal rate;
    }
}

