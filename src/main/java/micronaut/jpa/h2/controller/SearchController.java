package micronaut.jpa.h2.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import micronaut.jpa.h2.model.Vehicle;
import micronaut.jpa.h2.service.VehicleService;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

/**
 * A REST controller to search for vehicles
 */
@Controller
public class SearchController {

    @Inject
    private VehicleService vehicleService;

    /**
     * All parameters are optional
     *
     * @param make  Manufacturar
     * @param model model
     * @param year  Production year
     * @param color color
     * @return A list of vehicles if found, an empty list if not
     */
    @Get("/v1/search")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse search(@Nullable String make, @Nullable String model, @Nullable Integer year, @Nullable String color) {

        List<Vehicle> vehicles = vehicleService.findByParams(make, model, year, color);
        return HttpResponse.ok(vehicles);

    }

}
