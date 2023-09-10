package msraces.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private int raceCurrentPosition;
}
