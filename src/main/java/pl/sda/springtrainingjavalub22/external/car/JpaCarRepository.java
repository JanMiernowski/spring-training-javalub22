package pl.sda.springtrainingjavalub22.external.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.sda.springtrainingjavalub22.domain.car.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCarRepository extends JpaRepository<CarEntity, Long>, CustomDatabaseCarRepository {

    Optional<CarEntity> findByVin(String vin);

    List<CarEntity> findByManufacturer(String manufacturer);

    List<CarEntity> findByManufacturerAndModel(String manufacturer, String model);

    List<CarEntity> findByYearOfProductionGreaterThan(int yearOfProduction);

    void deleteByManufacturer(String manufacturer);

    Long countByYearOfProduction(int yearOfProduction);

    List<CarEntity> findByInsurance_InsuredToBefore(LocalDate lastEndDate);

//    @Query("select car from CarEntity car inner join car.insurance ins where ins.insuredFrom > :date and is.insuredTo < :date")
//    List<CarEntity> findInsuredCarsAtDay(@Param("date") LocalDate date);

    List<CarEntity> findByYearOfProductionLessThan(int yearOfProduction);

    List<CarEntity> findByModel(String model);

    @Query(value ="select count(*) from CarEntity car inner join car.insurance ins where :date not between ins.insuredFrom and ins.insuredTo ")
    Long findCarsNotInsuredInSpecificDay(@Param("date") LocalDate date);

    void deleteByVinStartsWith(String param);


}
