package msraces.services.raceServicesImpl;
import msraces.entities.Car;
import msraces.entities.Race;
import msraces.entities.Track;
import msraces.exceptions.InvalidCarToOvertakeException;
import msraces.services.raceServices.CarSelectionService;
import msraces.services.raceServices.RaceManagerService;
import msraces.services.raceServices.RaceResultPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RaceManagerServiceImpl implements RaceManagerService {
    private final CarSelectionService carSelectionService;
    private final RaceResultPublisher raceResultPublisher;

    private static final int START_POSITION = 1;
    private static final int OVERTAKE_ONE_CAR = 1;

    private List<Car> carsToRace;
    private Track track;

    @Autowired
    public RaceManagerServiceImpl(CarSelectionService carSelectionService, RaceResultPublisher raceResultPublisher) {
        this.carSelectionService = carSelectionService;
        this.raceResultPublisher = raceResultPublisher;
    }


    @Override
    public List<Car> startRace(Track track) {
        carsToRace = carSelectionService.getRandomCarsToRace();
        this.track = new Track(track.getName(), track.getCountry(), track.getDate());
        assignRandomRacePositions();
        return carsToRace;
    }

    @Override
    public List<Car> overtake(int position) {
        if (isValidOvertakePosition(position)) {
            Collections.swap(carsToRace, position, position + OVERTAKE_ONE_CAR);
            updateRacePositions(position, position + OVERTAKE_ONE_CAR);
        } else {
            throw new InvalidCarToOvertakeException("Invalid car to overtake");
        }
        return carsToRace;
    }

    @Override
    public Race finishRace() {
        Race race = new Race(null, track, carsToRace);
        raceResultPublisher.publishRaceResult(race);
        return race;
    }


    private void assignRandomRacePositions() {
        for (int i = 0; i < carsToRace.size(); i++) {
            carsToRace.get(i).setRaceCurrentPosition(i + START_POSITION);
        }
    }

    private boolean isValidOvertakePosition(int position) {
        int positionToOvertake = carsToRace.size() - 1;
        return position >= 0 && position < positionToOvertake;
    }

    private void updateRacePositions(int firstCarPosition, int secondCarPosition) {
        int newPosition = firstCarPosition + OVERTAKE_ONE_CAR;
        carsToRace.get(firstCarPosition).setRaceCurrentPosition(newPosition);
        carsToRace.get(secondCarPosition).setRaceCurrentPosition(newPosition + OVERTAKE_ONE_CAR);
    }


}
