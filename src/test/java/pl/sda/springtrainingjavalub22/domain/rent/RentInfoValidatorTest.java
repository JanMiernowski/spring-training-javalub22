package pl.sda.springtrainingjavalub22.domain.rent;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RentInfoValidatorTest {

    private RentInfoValidator validator = new RentInfoValidator();

    @Test
    public void shouldThrowExceptionWhenDatesAreNull() {
        //given
        RentInfo rentInfo = new RentInfo(null, null,
                BigDecimal.valueOf(100L),
                new HashSet<>(),
                new HashSet<>()
        );
        //when
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> validator.validateRentInfo(rentInfo));
        //then
        assertEquals("Dates should be passed", ex.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCarPriceIsNegative() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(1),
                BigDecimal.valueOf(-100L),
                new HashSet<>(),
                new HashSet<>()
        );
        //when
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> validator.validateRentInfo(rentInfo));
        //then
        assertEquals("Price cannot be negative", ex.getMessage());
    }


}
