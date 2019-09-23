package micronaut.jpa.h2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleJSONDTO {

    private Long dealerId;

    private String code;

    private String make;

    private String model;

    private Integer kW;

    private Integer ps;

    private Integer year;

    private String color;

    private Double price;

    @JsonProperty("kW")
    public Integer getkW() {
        return kW;
    }

    @JsonProperty("kW")
    public void setkW(Integer kW) {
        this.kW = kW;
    }

    public Vehicle toVehicleEntity() {
        return Vehicle.builder().id(VehicleId.builder().dealerId(dealerId).code(code).build()).make(make).model(model).kW(kW).ps(ps).year(year).color(color).price(price).build();
    }

    public static VehicleJSONDTO from(Vehicle vehicle) {
        return VehicleJSONDTO.builder().code(vehicle.getId().getCode()).dealerId(vehicle.getId().getDealerId()).make(vehicle.getMake()).model(vehicle.getModel()).kW(vehicle.getKW())
                .ps(vehicle.getPs()).year(vehicle.getYear()).color(vehicle.getColor()).price(vehicle.getPrice()).build();
    }

}
