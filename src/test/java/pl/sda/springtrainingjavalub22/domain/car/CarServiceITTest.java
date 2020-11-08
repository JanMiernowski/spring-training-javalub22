package pl.sda.springtrainingjavalub22.domain.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.springtrainingjavalub22.exception.AlreadyExistException;
import pl.sda.springtrainingjavalub22.external.car.CarEntity;
import pl.sda.springtrainingjavalub22.external.car.InsuranceEntity;
import pl.sda.springtrainingjavalub22.external.car.JpaCarRepository;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class CarServiceITTest {

    @Autowired
    private CarService carService;

    @Autowired
    private JpaCarRepository jpaCarRepository;

    @Test
    public void shouldPersistCarInDb() {
        //given
        LocalDate insuranceBegin = LocalDate.now();
        LocalDate insuranceEnd = insuranceBegin.minusMonths(6);
        Car car = new Car(null, "Skoda", "Fabia", 2015, "ABCD",
                insuranceBegin, insuranceEnd);
        //when
        carService.create(car);
        //then
        List<CarEntity> all = jpaCarRepository.findAll();
        Assertions.assertEquals(1, all.size());

        CarEntity addedCar = all.get(0);
        Assertions.assertEquals("Skoda", addedCar.getManufacturer());
        Assertions.assertEquals("Fabia", addedCar.getModel());
        Assertions.assertEquals(2015, addedCar.getYearOfProduction());
        Assertions.assertEquals("ABCD", addedCar.getVin());
        Assertions.assertEquals(insuranceBegin, addedCar.getInsurance().getInsuredFrom());
        Assertions.assertEquals(insuranceEnd, addedCar.getInsurance().getInsuredTo());
    }

    @Test
    public void shouldNotPersistCarWhenVinAlreadyExists() {
        //given
        LocalDate insuranceBegin = LocalDate.now();
        LocalDate insuranceEnd = insuranceBegin.minusMonths(6);
        Car car = new Car(null, "Skoda", "Fabia", 2015, "Vin1",
                insuranceBegin, insuranceEnd);

        CarEntity carEntity = new CarEntity(null, "VW", "Golf", 2010,
                "Vin1", new InsuranceEntity(null, insuranceBegin, insuranceEnd));
        jpaCarRepository.save(carEntity);
        //when
        AlreadyExistException ex = Assertions.assertThrows(AlreadyExistException.class,
                () -> carService.create(car));
        //then
        Assertions.assertEquals("Car with vin Vin1 already exists", ex.getMessage());
    }
}
