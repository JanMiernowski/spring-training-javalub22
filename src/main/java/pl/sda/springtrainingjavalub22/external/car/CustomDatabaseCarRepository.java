package pl.sda.springtrainingjavalub22.external.car;

import pl.sda.springtrainingjavalub22.api.model.SearchParams;

import java.util.List;

public interface CustomDatabaseCarRepository {

    List<CarEntity> findBasedOnSearchParams(SearchParams searchParams);
}
