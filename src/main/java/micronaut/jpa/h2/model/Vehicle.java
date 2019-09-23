package micronaut.jpa.h2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Vehicle {

    @Valid
    @EmbeddedId
    private VehicleId id;

    @NotEmpty(message = "make field cannot be empty")
    @Column(name = "make")
    private String make;

    @NotEmpty(message = "model field cannot be empty")
    @Column(name = "model")
    private String model;

    @Column(name = "kW")
    private Integer kW;

    @Column(name = "ps")
    private Integer ps;

    @NotNull(message = "year field cannot be empty")
    @Column(name = "year")
    private Integer year;

    @NotEmpty(message = "color field cannot be empty")
    @Column(name = "color")
    private String color;

    @NotNull(message = "price field cannot be empty")
    @Column(name = "price")
    private Double price;
}


