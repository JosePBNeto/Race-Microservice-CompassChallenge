package msraces.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msraces.entities.Car;
import msraces.entities.Race;
import msraces.entities.Track;
import msraces.feingClient.MsCarClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class RaceServiceImpl implements RaceService {


    private MsCarClient carClient;

    private final RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    private static final int MAX_CARS_TO_RACE = 10;
    private static final int MIN_CARS_TO_RACE = 3;

    private final Random random = new Random();

    private List<Car> carToRace;

    private Track track;

    @Autowired
    public RaceServiceImpl(MsCarClient carClient, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.carClient = carClient;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Car> getCars() {
        return getRandomCarsToRace();
    }

    @Override
    public List<Car> startRace(Track track) {
        this.track = new Track(track.getName(), track.getCountry(), track.getDate());
        assignRacePositions();
        return carToRace;
    }

    @Override
    public List<Car> overtake(int position) {
        if (isValidOvertakePosition(position)) {
            Collections.swap(carToRace, position, position + 1);
            updateRacePositions(position, position + 1);
        } else {
            throw new RuntimeException("Invalid overtaking position"); // TODO throw custom ex
        }
        return carToRace;
    }

    @Override
    public Race finishRace() {
        Race race = new Race(track, carToRace);
        try {
            String resultRaceString = objectMapper.writeValueAsString(race);
            // TODO: ADD LOGIC TO NOT SAVE REPEATED QUEUES
            rabbitTemplate.convertAndSend("race-result-queue", resultRaceString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO: add custom exc
        }
        return race;
    }

    private List<Car> getRandomCarsToRace() {
        List<Car> allCars = carClient.getAllCars();

        carToRace = new ArrayList<>();
        int randomCarsToRace = getRandomNumberInRange(MIN_CARS_TO_RACE, MAX_CARS_TO_RACE);

        List<Integer> selectedIndices = new ArrayList<>();

        while (carToRace.size() < randomCarsToRace) {
            int randomIndex = getRandomIndex(allCars.size());

            if (!selectedIndices.contains(randomIndex)) {
                selectedIndices.add(randomIndex);
                Car randomCar = allCars.get(randomIndex);
                carToRace.add(randomCar);
            }
        }

        return carToRace;
    }
    private void assignRacePositions() {
        for (int i = 0; i < carToRace.size(); i++) {
            carToRace.get(i).setRaceCurrentPosition(i + 1);
        }
    }

    private boolean isValidOvertakePosition(int position) {
        return position >= 0 && position < carToRace.size() - 1;
    }

    private void updateRacePositions(int firstCarPosition, int secondCarPosition) {
        carToRace.get(firstCarPosition).setRaceCurrentPosition(firstCarPosition + 1);
        carToRace.get(secondCarPosition).setRaceCurrentPosition(secondCarPosition + 1);
    }
    private int getRandomNumberInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private int getRandomIndex(int maxIndex) {
        return random.nextInt(maxIndex);
    }

}
