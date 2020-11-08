package pl.sda.springtrainingjavalub22.domain.car;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.springtrainingjavalub22.api.model.SearchParams;
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

    @BeforeEach
    public void cleanDb() {
        jpaCarRepository.deleteAll();
    }

    @Test
    public void shouldReturnEmptyListWhenNoCarsExistInDb() {
        //when
        List<Car> cars = carService.searchByParams(new SearchParams());
        //then
        Assertions.assertTrue(cars.isEmpty());
    }

    @Test
    public void shouldReturnByGivenManufacturer() {
        //given
        CarEntity carEntity1 = new CarEntity(null, "VW", "Golf", 2010,
                "Vin1", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        CarEntity carEntity2 = new CarEntity(null, "Skoda", "Golf", 2011,
                "Vin2", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        CarEntity carEntity3 = new CarEntity(null, "VW", "Passat", 2014,
                "Vin3", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        jpaCarRepository.saveAll(Lists.list(carEntity1, carEntity2, carEntity3));
        SearchParams searchParams = new SearchParams();
        searchParams.setManufacturer("VW");
        //when
        List<Car> carList = carService.searchByParams(searchParams);
        //then
        Assertions.assertEquals(2, carList.size());
        Assertions.assertTrue(carList.stream().allMatch(car -> car.getManufacturer().equals("VW")));
    }

    @Test
    public void shouldReturnOnlyNewerThanGivenProductionYear() {
        //given
        CarEntity carEntity1 = new CarEntity(null, "VW", "Golf", 2010,
                "Vin1", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        CarEntity carEntity2 = new CarEntity(null, "Skoda", "Golf", 2011,
                "Vin2", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        CarEntity carEntity3 = new CarEntity(null, "VW", "Passat", 2014,
                "Vin3", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        jpaCarRepository.saveAll(Lists.list(carEntity1, carEntity2, carEntity3));
        SearchParams searchParams = new SearchParams();
        searchParams.setProductionFrom(2012);
        //when
        List<Car> cars = carService.searchByParams(searchParams);
        //then
        Assertions.assertEquals(1, cars.size());
        Assertions.assertTrue(cars.stream().allMatch(car -> car.getYearOfProduction() > 2012));
    }

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
