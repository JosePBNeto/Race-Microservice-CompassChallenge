package msraces.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import msraces.entities.Pilot;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cars {
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String brand;
    private String model;
    @JsonIgnoreProperties({"id", "age"})
    private Pilot pilot;
    @JsonIgnore
    private String year;

    private int raceCurrentPosition;
}
