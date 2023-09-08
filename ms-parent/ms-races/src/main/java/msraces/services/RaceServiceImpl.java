package msraces.services;

import msraces.dtos.Cars;
import msraces.feingClient.MsCarClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RaceServiceImpl implements RaceService{

    @Autowired
    private MsCarClient carClient;

    private static final int MAX_OF_CARS_TO_RACE = 10;
    private static final int MIN_OF_CARS_TO_RACE = 3;

    Random random = new Random();

    @Override
    public List<Cars> getCars() {
        return getRandomCarsToRace();
    }
    public void simulateRace(){

    }




    public List<Cars> getRandomCarsToRace(){

        List<Cars> allCars = carClient.getAllCars();
        List<Cars> carsToRace = new ArrayList<>();


        int randomCarsToRace = random.nextInt(MAX_OF_CARS_TO_RACE - MIN_OF_CARS_TO_RACE + 1) + MIN_OF_CARS_TO_RACE;
        Set<Integer> selectedIndices = new HashSet<>();

        while (carsToRace.size() < randomCarsToRace) {
            int randomIndex = random.nextInt(randomCarsToRace);

            if (!selectedIndices.contains(randomIndex)) {
                selectedIndices.add(randomIndex);
                Cars randomCar = allCars.get(randomIndex);
                carsToRace.add(randomCar);
            }
        }
        return carsToRace;
    }



}
