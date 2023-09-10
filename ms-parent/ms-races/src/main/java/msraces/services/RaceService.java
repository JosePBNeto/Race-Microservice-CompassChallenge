package msraces.services;

import msraces.entities.Car;
import msraces.entities.Race;
import msraces.entities.Track;

import java.util.List;
public interface RaceService {

    List<Car> getCars();

    List<Car> startRace(Track track);

    List<Car> overtake(int position);

    Race finishRace();
}
