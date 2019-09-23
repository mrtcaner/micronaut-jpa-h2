package micronaut.jpa.h2.repository;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import micronaut.jpa.h2.model.Vehicle;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository
public abstract class AbstractVehicleRepository implements CrudRepository<Vehicle, Long>, CustomVehicleRepository {

    private final EntityManager entityManager;

    public AbstractVehicleRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Vehicle> findByQueryParams(String make, String model, Integer year, String color) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vehicle> cr = cb.createQuery(Vehicle.class);
        Root<Vehicle> root = cr.from(Vehicle.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(make)) {
            predicates.add(cb.like(root.get("make"), make));
        }

        if (!StringUtils.isEmpty(model)) {
            predicates.add(cb.like(root.get("model"), model));
        }

        if (!StringUtils.isEmpty(color)) {
            predicates.add(cb.like(root.get("model"), color));
        }

        if (null != year) {
            predicates.add(cb.like(root.get("year"), Integer.toString(year)));
        }

        if (CollectionUtils.isEmpty(predicates)) {
            return new ArrayList<>();
        }

        Predicate[] predicatesArr = predicates.toArray(new Predicate[predicates.size()]);
        cr.select(root).where(predicatesArr);
        TypedQuery<Vehicle> query = entityManager.createQuery(cr);
        return query.getResultList();
    }

}