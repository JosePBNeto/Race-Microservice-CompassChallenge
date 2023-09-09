package msraces.services;

import msraces.dtos.Cars;
import msraces.feingClient.MsCarClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class RaceServiceImpl implements RaceService {

    @Autowired
    private MsCarClient carClient;

    private static final int MAX_CARS_TO_RACE = 10;
    private static final int MIN_CARS_TO_RACE = 3;

    private final Random random = new Random();
    private List<Cars> carsToRace;

    @Override
    public List<Cars> getCars() {
        return getRandomCarsToRace();
    }

    @Override
    public List<Cars> startRace() {
        assignRacePositions();
        return carsToRace;
    }

    @Override
    public List<Cars> overtake(int position) {
        if (isValidOvertakePosition(position)) {
            Collections.swap(carsToRace, position, position + 1);
            updateRacePositions(position, position + 1);
        } else {
            throw new RuntimeException("Invalid overtaking position"); // TODO throw custom ex
        }
        return carsToRace;
    }

    private List<Cars> getRandomCarsToRace() {
        List<Cars> allCars = carClient.getAllCars();
        carsToRace = new ArrayList<>();
        int randomCarsToRace = getRandomNumberInRange(MIN_CARS_TO_RACE, MAX_CARS_TO_RACE);

        List<Integer> selectedIndices = new ArrayList<>();

        while (carsToRace.size() < randomCarsToRace) {
            int randomIndex = getRandomIndex(allCars.size());

            if (!selectedIndices.contains(randomIndex)) {
                selectedIndices.add(randomIndex);
                Cars randomCar = allCars.get(randomIndex);
                carsToRace.add(randomCar);
            }
        }

        return carsToRace;
    }
    private void assignRacePositions() {
        for (int i = 0; i < carsToRace.size(); i++) {
            carsToRace.get(i).setRaceCurrentPosition(i + 1);
        }
    }

    private boolean isValidOvertakePosition(int position) {
        return position >= 0 && position < carsToRace.size() - 1;
    }

    private void updateRacePositions(int firstCarPosition, int secondCarPosition) {
        carsToRace.get(firstCarPosition).setRaceCurrentPosition(firstCarPosition + 1);
        carsToRace.get(secondCarPosition).setRaceCurrentPosition(secondCarPosition + 1);
    }
    private int getRandomNumberInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private int getRandomIndex(int maxIndex) {
        return random.nextInt(maxIndex);
    }
}
