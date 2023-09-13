package mscars.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mscars.entity.PilotEntity;


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
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Year should be in the format YYYY (e.g., 2022)")
    private String year;
}
