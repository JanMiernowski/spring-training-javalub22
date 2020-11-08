package pl.sda.springtrainingjavalub22.domain.rent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RentPriceCalculatorTest {

    private RentInfoValidator validator = Mockito.mock(RentInfoValidator.class);
    private ExchangeRateRepository rateRepository = Mockito.mock(ExchangeRateRepository.class);

    private RentPriceCalculator calculator = new RentPriceCalculator(validator, rateRepository);

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
        Assertions.assertEquals(BigDecimal.valueOf(990L).setScale(2), price.setScale(2));
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
        Assertions.assertEquals(BigDecimal.valueOf(375L).setScale(2), price.setScale(2));
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
        Assertions.assertEquals(BigDecimal.valueOf(250L).setScale(2), price.setScale(2));
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
        Assertions.assertEquals(BigDecimal.valueOf(600L).setScale(2), price.setScale(2));
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
        Assertions.assertEquals(BigDecimal.valueOf(750L).setScale(2), price.setScale(2));
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
        Assertions.assertEquals(BigDecimal.valueOf(450L).setScale(2), price.setScale(2));
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
        Assertions.assertEquals(BigDecimal.valueOf(500L).setScale(2), price.setScale(2));
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
        assertEquals(BigDecimal.valueOf(100L).setScale(2), price.setScale(2));
    }

    @Test
    public void shouldThrowExceptionWhenExchangeRateIsNotPresentForGivenCurrency() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(11),
                BigDecimal.valueOf(100L), new HashSet<>(), new HashSet<>());
        //when
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> calculator.calculatePriceInDifferentCurrency(rentInfo, "USD"));
        //then
        assertEquals("There is no rate for currency USD", ex.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenGivenCurrencyIsNotPresent() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(11),
                BigDecimal.valueOf(100L), new HashSet<>(), new HashSet<>());
        //when
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculatePriceInDifferentCurrency(rentInfo, ""));
        //then
        assertEquals("Currency must be present", ex.getMessage());
    }

    @Test
    public void shouldCalculatePriceInDifferentCurrency() {
        //given
        RentInfo rentInfo = new RentInfo(LocalDate.now(), LocalDate.now().plusDays(8),
                BigDecimal.valueOf(100L), new HashSet<>(), new HashSet<>());

        Mockito.when(rateRepository.getExchangeRate("USD")).thenReturn(BigDecimal.valueOf(0.25));
        //zamiast konkretnej wartości Mockito.any()
        //when
        BigDecimal price = calculator.calculatePriceInDifferentCurrency(rentInfo, "USD");
        //then
        assertEquals(BigDecimal.valueOf(200).setScale(2), price.setScale(2));
        //TO sprawdzi czy ta metoda zostala wykonana dokladnie 1 raz
        Mockito.verify(rateRepository, Mockito.times(1)).getExchangeRate("USD");
        //Weryfikuje dodatkowo czy nie wywoluje zadnej metody na tym obiekcie z innymi parametrami
        Mockito.verifyNoMoreInteractions(rateRepository);
    }
}
