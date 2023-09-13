package msraces.services.raceServicesImpl;

import msraces.dtos.CarDtoResponse;
import msraces.dtos.RaceResultResponse;
import msraces.entities.Car;
import msraces.entities.Pilot;
import msraces.entities.Race;
import msraces.entities.Track;
import msraces.services.raceServices.CarSelectionService;
import msraces.services.raceServices.RaceResultPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RaceManagerServiceImplTest {

    @Mock
    private CarSelectionService carSelectionService;

    @Mock
    private RaceResultPublisher raceResultPublisher;

    @InjectMocks
    private RaceManagerServiceImpl raceManagerService;


    @Test
    void testStartRace() {
        Pilot pilot = Pilot.builder()
                .name("Jose")
                .age("27")
                .build();
        Track track = Track.builder()
                .name("Interlagos")
                .country("Brazil")
                .date(null)
                .build();
        List<Car> cars = List.of(Car.builder()
                        .id("abc123")
                        .brand("Golf")
                        .model("GTI")
                        .pilot(pilot)
                        .build(),
                Car.builder()
                        .id("csd3234")
                        .brand("Chevrolet")
                        .model("Sonic")
                        .pilot(pilot)
                        .build(),
                Car.builder()
                        .id("avc345")
                        .brand("Chevrolet")
                        .model("Camaro")
                        .pilot(pilot)
                        .build());

        when(carSelectionService.getRandomCarsToRace()).thenReturn(cars);

        List<Car> result = raceManagerService.startRace(track);

        assertNotNull(result);
        assertEquals(cars, result);

        verify(carSelectionService, times(1)).getRandomCarsToRace();
    }
    @Test
    void testFinishRace() {
        Pilot pilot = Pilot.builder()
                .name("Jose")
                .age("27")
                .build();
        Track track = Track.builder()
                .name("Interlagos")
                .country("Brazil")
                .date(null)
                .build();
        List<Car> cars = List.of(Car.builder()
                        .id("abc123")
                        .brand("Golf")
                        .model("GTI")
                        .pilot(pilot)
                        .build(),
                Car.builder()
                        .id("csd3234")
                        .brand("Chevrolet")
                        .model("Sonic")
                        .pilot(pilot)
                        .build(),
                Car.builder()
                        .id("avc345")
                        .brand("Chevrolet")
                        .model("Camaro")
                        .pilot(pilot)
                        .build());

        when(carSelectionService.getRandomCarsToRace()).thenReturn(cars);


        List<Car> raceResult = raceManagerService.startRace(track);


        RaceResultResponse raceResultResponse = raceManagerService.finishRace();

        assertEquals(track.getName(), raceResultResponse.getTrack().getName());
        assertEquals(cars.size(), raceResultResponse.getCars().size());

        for (int i = 0; i < cars.size(); i++) {
            Car testCar = cars.get(i);
            CarDtoResponse dtoResponse = raceResultResponse.getCars().get(i);

            assertEquals(testCar.getModel(), dtoResponse.getModel());
            assertEquals(testCar.getBrand(), dtoResponse.getBrand());
            assertEquals(testCar.getPilot(), dtoResponse.getPilot());
            assertEquals(testCar.getRaceCurrentPosition(), dtoResponse.getFinishPosition());
        }
    }


}