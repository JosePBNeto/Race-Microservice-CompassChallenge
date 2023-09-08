package mscars.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mscars.entity.PilotEntity;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarDtoRequest {

    @NotBlank(message = "Brand must not be blank")
    private String brand;
    @NotBlank(message = "Model must not be blank")
    private String model;
    @NotBlank(message = "Pilot must not be blank")
    private PilotEntity pilot;
    @NotBlank(message = "Year must not be blank")
    private Date year;
}
