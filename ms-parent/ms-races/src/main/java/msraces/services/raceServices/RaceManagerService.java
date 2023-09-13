package msraces.services.raceServices;

import msraces.dtos.RaceResultResponse;
import msraces.entities.Car;
import msraces.entities.Track;

import java.util.List;
public interface RaceManagerService {

    List<Car> startRace(Track track);

    List<Car> overtake(int position);

    RaceResultResponse finishRace();
}
