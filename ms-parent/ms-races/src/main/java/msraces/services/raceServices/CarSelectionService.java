package msraces.services.raceServices;

import msraces.entities.Car;

import java.util.List;

public interface CarSelectionService {
    List<Car> getRandomCarsToRace();

}
