package pl.sda.springtrainingjavalub22.api;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.sda.springtrainingjavalub22.api.model.SearchParams;
import pl.sda.springtrainingjavalub22.config.RestTemplateConfig;
import pl.sda.springtrainingjavalub22.domain.car.Car;
import pl.sda.springtrainingjavalub22.external.car.CarEntity;
import pl.sda.springtrainingjavalub22.external.car.InsuranceEntity;
import pl.sda.springtrainingjavalub22.external.car.JpaCarRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = RestTemplateConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarApiITTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JpaCarRepository carRepository;

    @BeforeEach
    public void cleanDb() {
        carRepository.deleteAll();
    }

    @Test
    public void shouldDeleteCarFromDb() {
        //given
        CarEntity carEntity = new CarEntity(null, "VW", "Golf", 2010,
                "Vin1", new InsuranceEntity(null, LocalDate.now(), LocalDate.now()));
        CarEntity persistedCar = carRepository.save(carEntity);
        //when
        restTemplate.delete(String.format("http://localhost:%d/api/car?carId=%d", port, persistedCar.getId()));
        //then
        Assertions.assertTrue(carRepository.count() == 0);
    }

    @Test
    public void shouldNotPassValidationWhenCreatingCar() {
        //given
        Car newCar = new Car(null, "", "Focus", -2015, "123451",
                LocalDate.now(), LocalDate.now().plusWeeks(5));
        HttpEntity<Car> entity = new HttpEntity<>(newCar);
        //when
        ResponseEntity<List> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/car", port),
                HttpMethod.POST, entity, List.class);
        //then
        Assertions.assertEquals(400, rsp.getStatusCodeValue());

        List errors = rsp.getBody();
        Assertions.assertTrue(errors.contains("Manufacturer should be not blank"));
        Assertions.assertTrue(errors.contains("Year of production could not be negative"));
        Assertions.assertTrue(errors.contains("Vin should not be empty and should have 10 signs"));
    }

    @Test
    public void shouldCreateCar() {
        //given
        Car newCar = new Car(null, "Ford", "Focus", 2015, "1234567890",
                LocalDate.now(), LocalDate.now().plusWeeks(5));
        HttpEntity<Car> entity = new HttpEntity<>(newCar);
        //when
        ResponseEntity<Void> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/car", port),
                HttpMethod.POST, entity, Void.class);
        //then
        Assertions.assertEquals(201, rsp.getStatusCodeValue());

        List<CarEntity> all = carRepository.findAll();
        Assertions.assertEquals(1, all.size());

        CarEntity persistedCar = all.get(0);
        Assertions.assertEquals("Ford", persistedCar.getManufacturer());
        Assertions.assertEquals("Focus", persistedCar.getModel());
        Assertions.assertEquals("1234567890", persistedCar.getVin());
        Assertions.assertEquals(2015, persistedCar.getYearOfProduction());
    }

    @Test
    public void shouldGetAllCarsFromDb() {
        //given
        CarEntity carEntity1 = new CarEntity(null, "VW", "Golf", 2010,
                "Vin1", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        CarEntity carEntity2 = new CarEntity(null, "Skoda", "Golf", 2011,
                "Vin2", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        CarEntity carEntity3 = new CarEntity(null, "VW", "Passat", 2014,
                "Vin3", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        carRepository.saveAll(Lists.list(carEntity1, carEntity2, carEntity3));
        //when
        ResponseEntity<List> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/car", port),
                HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), List.class);
        //then
        Assertions.assertEquals(200, rsp.getStatusCodeValue());
        Assertions.assertEquals(3, rsp.getBody().size());
    }

    @Test
    public void shouldUpdateCar() {
        //given
        CarEntity carEntity = new CarEntity(null, "VW", "Golf", 2010,
                "1234567890", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        CarEntity persistedCar = carRepository.save(carEntity);

        Car updateRequest = new Car(persistedCar.getId(),
                "Ford", "Focus", 2015, "1234567890",
                LocalDate.now(), LocalDate.now().plusWeeks(5));

        HttpEntity<Car> entity = new HttpEntity<>(updateRequest);
        //when
        ResponseEntity<Void> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/car", port),
                HttpMethod.PUT, entity, Void.class);
        //then
        Assertions.assertEquals(200, rsp.getStatusCodeValue());

        CarEntity updatedCar = carRepository.findById(persistedCar.getId()).get();
        Assertions.assertEquals("Ford", updatedCar.getManufacturer());
        Assertions.assertEquals("Focus", updatedCar.getModel());
        Assertions.assertEquals(2015, updatedCar.getYearOfProduction());
    }

    @Test
    public void shouldGetCarsByManufacturer() {
        //given
        CarEntity carEntity1 = new CarEntity(null, "VW", "Golf", 2010,
                "Vin1", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        CarEntity carEntity2 = new CarEntity(null, "Skoda", "Golf", 2011,
                "Vin2", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        CarEntity carEntity3 = new CarEntity(null, "VW", "Passat", 2014,
                "Vin3", new InsuranceEntity(null, LocalDate.now(), LocalDate.now().plusMonths(6)));
        carRepository.saveAll(Lists.list(carEntity1, carEntity2, carEntity3));

        SearchParams searchParams = new SearchParams();
        searchParams.setManufacturer("VW");

        HttpEntity<SearchParams> entity = new HttpEntity<>(searchParams);
        //when
        ResponseEntity<List<Car>> rsp = restTemplate.exchange(String.format("http://localhost:%d/api/car/search", port),
                HttpMethod.POST, entity, new ParameterizedTypeReference<List<Car>>() {});
        //then
        Assertions.assertEquals(200, rsp.getStatusCodeValue());
        Assertions.assertEquals(2, rsp.getBody().size());
        Assertions.assertTrue(rsp.getBody().stream().allMatch(car -> car.getManufacturer().equals("VW")));
    }
}
