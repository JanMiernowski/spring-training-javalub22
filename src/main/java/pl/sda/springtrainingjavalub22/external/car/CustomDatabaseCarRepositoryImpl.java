package pl.sda.springtrainingjavalub22.external.car;

import pl.sda.springtrainingjavalub22.api.model.SearchParams;
import pl.sda.springtrainingjavalub22.domain.car.Car;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CustomDatabaseCarRepositoryImpl implements CustomDatabaseCarRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CarEntity> findBasedOnSearchParams(SearchParams searchParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CarEntity> query = criteriaBuilder.createQuery(CarEntity.class);
        Root<CarEntity> root = query.from(CarEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchParams.getManufacturer() != null && !searchParams.getManufacturer().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("manufacturer"), searchParams.getManufacturer()));
        }
        //... TUTAJ POWINNY ZNALEZC SIE POZOSTALE WARUNKI
        if (searchParams.getProductionFrom() != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("yearOfProduction"), searchParams.getProductionFrom()));
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }
}
