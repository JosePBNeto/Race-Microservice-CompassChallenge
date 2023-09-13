package msraces.services.raceServicesImpl;

import msraces.entities.Car;
import msraces.exceptions.InsufficientCarToStartRaceException;
import msraces.feingClient.MsCarClient;
import msraces.services.raceServices.CarSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarSelectionServiceImpl implements CarSelectionService {

    private final MsCarClient carClient;
    private final Random random = new Random();

    private static final int MAX_CARS_TO_RACE = 10;
    private static final int MIN_CARS_TO_RACE = 3;

    @Autowired
    public CarSelectionServiceImpl(MsCarClient carClient) {
        this.carClient = carClient;
    }

    @Override
    public List<Car> getRandomCarsToRace() {
        List<Car> allCars = carClient.getAllCars();

        if (allCars.size() < MIN_CARS_TO_RACE){
            throw new InsufficientCarToStartRaceException("Insufficient number cars needed to start a race. MINIMUM = 3");
        }

        int randomCarsToRace = getRandomNumberInRange(MIN_CARS_TO_RACE, MAX_CARS_TO_RACE);


        Set<Integer> selectedIndices = new HashSet<>();
        ArrayList<Car> carsToRace = new ArrayList<>();

        while (carsToRace.size() < randomCarsToRace) {
            int randomIndex = getRandomIndex(allCars.size());

            if (selectedIndices.add(randomIndex)) {
                carsToRace.add(allCars.get(randomIndex));
            }
        }

        return carsToRace;
    }

    private int getRandomNumberInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private int getRandomIndex(int maxIndex) {
        return random.nextInt(maxIndex);
    }
}
