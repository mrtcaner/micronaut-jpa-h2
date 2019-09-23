package micronaut.jpa.h2.service;


import micronaut.jpa.h2.model.Vehicle;

import java.util.List;

public interface VehicleService {

    Iterable<Vehicle> saveOrUpdateAll(List<Vehicle> vehicles);

    List<Vehicle> findByParams(String make, String model, Integer year, String color);
}
