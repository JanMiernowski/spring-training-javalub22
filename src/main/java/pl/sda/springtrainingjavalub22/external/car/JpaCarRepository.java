package pl.sda.springtrainingjavalub22.external.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCarRepository extends JpaRepository<CarEntity, Long>, CustomDatabaseCarRepository {

    //select * from car where vin = ?1
    Optional<CarEntity> findByVin(String vin);

    //select * from car where manufcaturer = ?1
    List<CarEntity> findByManufacturer(String manufacturer);

    //select * from car where manufacturer = ?1 and model = ?2
    List<CarEntity> findByManufacturerAndModel(String manufacturer, String model);

    //select * from car where year_of_production > ?1
    List<CarEntity> findByYearOfProductionGreaterThan(int yearOfProduction);

    //delete from cars where manufacturer = ?1
    void deleteByManufacturer(String manufacturer);

    //select count(*) from cars where yearOfProduction = ?1
    Long countByYearOfProduction(int yearOfProduction);

    //select c from cars c inner join insurance ins on c.insurance_id = ins.id where ins.insured_from < ?1
    List<CarEntity> findByInsurance_InsuredToBefore(LocalDate lastEndDate);

    @Query("select car from CarEntity car inner join car.insurance ins " +
            "where ins.insuredFrom < :date and ins.insuredTo > :date")
    List<CarEntity> findInsuredCarsAtDay(@Param("date") LocalDate date);

    //1
    List<CarEntity> findByYearOfProductionIsLessThan(Integer yearOfProduction);
    //2
    List<CarEntity> findByModel(String model);

    //3
    @Query("select count(car) from CarEntity car inner join car.insurance ins " +
            "where ins.insuredFrom > :date or ins.insuredTo > :date")
    Long countNotInsuredAtDate(LocalDate date);

    //4
    void deleteByVinStartsWith(String vinPrefix);

    //5
    List<CarEntity> findByYearOfProductionIsBetween(Integer productionStart, Integer productionEnd);
}









