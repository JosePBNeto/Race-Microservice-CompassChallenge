package msraces.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import msraces.entities.Pilot;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cars {
    private String id;
    private String brand;
    private String model;
    @JsonIgnoreProperties({"id"})
    private Pilot pilot;
    private Date year;

    private int distance;
}
