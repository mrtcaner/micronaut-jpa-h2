package micronaut.jpa.h2.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import micronaut.jpa.h2.util.csv.CsvBean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCvsDTO extends CsvBean {

    private Long dealerId;

    @CsvBindByName(column = "code")
    private String code;

    @CsvBindByName(column = "make/model")
    private String makeModel;

    private Integer kW;

    @CsvBindByName(column = "power-in-ps")
    private Integer ps;

    @CsvBindByName(column = "year")
    private Integer year;

    @CsvBindByName(column = "color")
    private String color;

    @CsvBindByName(column = "price")
    private Double price;

    public Vehicle toVehicleEntity() {
        return Vehicle.builder().id(VehicleId.builder().dealerId(dealerId).code(code).build()).make(makeModel.split("/")[0]).model(makeModel.split("/")[1]).kW(kW).ps(ps).year(year).color(color).price(price).build();
    }

    public static VehicleJSONDTO toVehicleDTO(Vehicle vehicle) {
        return VehicleJSONDTO.builder().code(vehicle.getId().getCode()).dealerId(vehicle.getId().getDealerId()).make(vehicle.getMake()).model(vehicle.getModel()).kW(vehicle.getKW())
                .ps(vehicle.getPs()).year(vehicle.getYear()).color(vehicle.getColor()).price(vehicle.getPrice()).build();
    }

}


