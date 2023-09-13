package mscars.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mscars.entity.PilotEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDtoResponse {
    private String id;
    private String brand;
    private String model;
    private PilotEntity pilot;
    private String year;
}
