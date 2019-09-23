package micronaut.jpa.h2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleId implements Serializable {

    @Column(name = "dealerId")
    @NotNull
    private Long dealerId;

    @Column(name = "code")
    @NotEmpty
    @NotNull
    private String code;
}