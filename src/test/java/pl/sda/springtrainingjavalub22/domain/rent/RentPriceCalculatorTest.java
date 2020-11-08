package pl.sda.springtrainingjavalub22.domain.rent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RentPriceCalculatorTest {

    private RentPriceCalculator calculator = new RentPriceCalculator();

    @Test
    public void shouldThrowExceptionWhenRentPeriodIsShortenThanOneDay() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now(), BigDecimal.TEN,
                new HashSet<>(), new HashSet<>());
        //when
        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> calculator.calculatePrice(rentInfo));
        //then
        Assertions.assertEquals("Period should be at least 1 day", exception.getMessage());
    }

    @Test
    public void shouldCalculatePriceWhenRentPeriodIsLongerThan10Days() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(11),
                BigDecimal.valueOf(100L), new HashSet<>(), new HashSet<>());
        //when
        BigDecimal price = calculator.calculatePrice(rentInfo);
        //then
        Assertions.assertEquals(BigDecimal.valueOf(990L), price);
    }

    @Test
    public void shouldCalculatePriceWhenDiscountsArePresent() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(5),
                BigDecimal.valueOf(100L),
                    Sets.newSet(
                            new RentInfo.Discount("Christmas", BigDecimal.valueOf(10L)),
                            new RentInfo.Discount("New customer", BigDecimal.valueOf(15L))),
                new HashSet<>());
        //when
        BigDecimal price = calculator.calculatePrice(rentInfo);
        //then
        Assertions.assertEquals(BigDecimal.valueOf(375L), price);
    }

    //Co się stanie gdy zniżki przekroczą 50%?
    @Test
    public void shouldCalculatePriceWhenDiscountsAreHigherThan50Percent() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(5),
                BigDecimal.valueOf(100L),
                Sets.newSet(
                        new RentInfo.Discount("Christmas", BigDecimal.valueOf(40L)),
                        new RentInfo.Discount("New customer", BigDecimal.valueOf(15L))),
                new HashSet<>());
        //when
        BigDecimal price = calculator.calculatePrice(rentInfo);
        //then
        Assertions.assertEquals(BigDecimal.valueOf(250L), price);
    }

    @Test
    public void shouldCalculatePriceWhenIncreasesArePresent() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(5),
                BigDecimal.valueOf(100L),
                new HashSet<>(),
                Sets.newSet(
                        new RentInfo.Increase("Premium car", BigDecimal.valueOf(5L)),
                        new RentInfo.Increase("Young driver", BigDecimal.valueOf(15L)))
                );
        //when
        BigDecimal price = calculator.calculatePrice(rentInfo);
        //then
        Assertions.assertEquals(BigDecimal.valueOf(600L), price);
    }

    @Test
    public void shouldCalculatePriceWhenIncreasesAreHigherThan50Percent() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(5),
                BigDecimal.valueOf(100L),
                new HashSet<>(),
                Sets.newSet(
                        new RentInfo.Increase("Premium car", BigDecimal.valueOf(50L)),
                        new RentInfo.Increase("Young driver", BigDecimal.valueOf(15L)))
                );
        //when
        BigDecimal price = calculator.calculatePrice(rentInfo);
        //then
        Assertions.assertEquals(BigDecimal.valueOf(750L), price);
    }

    @Test
    public void shouldCalculatePriceWhenIncreasesAndDiscountsArePresent() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(5),
                BigDecimal.valueOf(100L),
                Sets.newSet(
                        new RentInfo.Discount("Christmas", BigDecimal.valueOf(15L))),
                Sets.newSet(
                        new RentInfo.Increase("Premium car", BigDecimal.valueOf(5L)))
        );
        //when
        BigDecimal price = calculator.calculatePrice(rentInfo);
        //then
        Assertions.assertEquals(BigDecimal.valueOf(450L), price);
    }

    @Test
    public void shouldCalculatePriceWhenRentPeriodIsShortenThan10Days() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(5),
                BigDecimal.valueOf(100L),
                new HashSet<>(),
                new HashSet<>()
        );
        //when
        BigDecimal price = calculator.calculatePrice(rentInfo);
        //then
        Assertions.assertEquals(BigDecimal.valueOf(500L), price);
    }

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
                assertThrows(IllegalArgumentException.class, () -> calculator.calculatePrice(rentInfo));
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
                assertThrows(IllegalArgumentException.class, () -> calculator.calculatePrice(rentInfo));
        //then
        assertEquals("Price cannot be negative", ex.getMessage());
    }

    @Test
    public void shouldSkipNullDiscountAndIncrease() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(1),
                BigDecimal.valueOf(100L),
                Sets.newSet(new RentInfo.Discount("Christmas", null)),
                Sets.newSet(new RentInfo.Increase("Young drivier", null))
        );
        //when
        BigDecimal price = calculator.calculatePrice(rentInfo);
        //then
        assertEquals(BigDecimal.valueOf(100L), price);
    }
}
