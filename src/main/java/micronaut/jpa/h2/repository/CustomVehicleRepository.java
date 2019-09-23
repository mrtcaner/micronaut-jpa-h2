package micronaut.jpa.h2.repository;

import micronaut.jpa.h2.model.Vehicle;

import java.util.List;

public interface CustomVehicleRepository {

    List<Vehicle> findByQueryParams(String make, String model, Integer year, String color);

}
