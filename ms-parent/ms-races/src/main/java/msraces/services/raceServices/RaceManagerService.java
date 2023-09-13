package msraces.services.raceServices;

import msraces.entities.Car;
import msraces.entities.Race;
import msraces.entities.Track;

import java.util.List;
public interface RaceManagerService {

    List<Car> startRace(Track track);

    List<Car> overtake(int position);

    Race finishRace();
}
