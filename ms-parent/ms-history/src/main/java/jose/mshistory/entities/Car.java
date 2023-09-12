package jose.mshistory.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {

    private String id;

    private String brand;
    private String model;

    private Pilot pilot;

    private String year;

    @JsonProperty("finishPosition")
    @Field("finishPosition")
    private int raceCurrentPosition;
}
