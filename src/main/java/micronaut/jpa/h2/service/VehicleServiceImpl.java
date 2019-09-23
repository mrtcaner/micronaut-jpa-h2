package micronaut.jpa.h2.service;

import io.micronaut.spring.tx.annotation.Transactional;
import micronaut.jpa.h2.model.Vehicle;
import micronaut.jpa.h2.repository.AbstractVehicleRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class VehicleServiceImpl implements VehicleService {

    @Inject
    private AbstractVehicleRepository vehicleRepository;

    @Transactional
    @Override
    public Iterable<Vehicle> saveOrUpdateAll(List<Vehicle> vehicles) {
        return vehicleRepository.saveAll(vehicles);
    }

    @Transactional
    @Override
    public List<Vehicle> findByParams(String make, String model, Integer year, String color) {
        return vehicleRepository.findByQueryParams(make, model, year, color);
    }

}
