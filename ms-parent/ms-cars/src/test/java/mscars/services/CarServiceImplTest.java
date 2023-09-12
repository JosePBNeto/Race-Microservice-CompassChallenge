package mscars.services;

import mscars.dtos.CarDtoRequest;
import mscars.dtos.CarDtoResponse;
import mscars.entity.CarEntity;
import mscars.entity.PilotEntity;
import mscars.exceptions.IdNotFoundException;
import mscars.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;
    @Mock
    private CarRepository carRepository;

    @Spy
    private ModelMapper modelMapper;

    private static final String EXISTING_CAR_ID = "existingCarId";
    private static final String NON_EXISTING_CAR_ID = "nonExistingCarId";

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        PilotEntity pilot = PilotEntity.builder().name("Jao").age("23")
                .build();
        CarDtoRequest carDtoRequest = CarDtoRequest.builder().model("Focus").brand("Ford").pilot(pilot).year(null)
                .build();

        CarEntity carEntity = CarEntity.builder().id(EXISTING_CAR_ID).brand("Ford").model("Focus").year(null).pilot(pilot)
                .build();

        when(carRepository.save(any())).thenReturn(carEntity);

        CarDtoResponse response = carService.save(carDtoRequest);


        assertEquals(carDtoRequest.getModel(), response.getModel());
        assertEquals(carDtoRequest.getPilot(), response.getPilot());


    }

    @Test
    void testGetAllCars() {
        PilotEntity pilot = PilotEntity.builder().name("Jao").age("23")
                .build();
        CarEntity carEntity = CarEntity.builder().id(EXISTING_CAR_ID).brand("Ford").model("Focus").year(null).pilot(pilot)
                .build();
        when(carRepository.findAll()).thenReturn(Collections.singletonList(carEntity));

        List<CarDtoResponse> carDtoResponses = carService.getAllCars();

        assertNotNull(carDtoResponses);
        assertEquals(1, carDtoResponses.size());
        assertEquals(EXISTING_CAR_ID, carDtoResponses.get(0).getId());
        assertEquals(carEntity.getBrand(), carDtoResponses.get(0).getBrand());
        assertEquals(carEntity.getModel(), carDtoResponses.get(0).getModel());

    }

    @Test
    void findCarById() {
        PilotEntity pilot = PilotEntity.builder().name("Jao").age("23")
                .build();
        CarEntity carEntity = CarEntity.builder().id(EXISTING_CAR_ID).brand("Ford").model("Focus").year(null).pilot(pilot)
                .build();

        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(carEntity));


        CarDtoResponse response = carService.findCarById(EXISTING_CAR_ID);

        assertNotNull(response);
        assertEquals(EXISTING_CAR_ID, response.getId());
        assertEquals(carEntity.getModel(), response.getModel());

    }

    @Test
    void findCarByIdNonExisting() {
        when(carRepository.findById(NON_EXISTING_CAR_ID)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> carService.findCarById(NON_EXISTING_CAR_ID));
    }

    @Test
    void updateCar() {
        PilotEntity pilot = PilotEntity.builder().name("Jao").age("23")
                .build();
        CarDtoRequest carDtoRequest = CarDtoRequest.builder().model("Focus").brand("Ford").pilot(pilot).year(null)
                .build();
        CarEntity carEntity = CarEntity.builder().id(EXISTING_CAR_ID).brand("Ford").model("Focus").year(null).pilot(pilot)
                .build();
        CarEntity updateCarEntity = CarEntity.builder().id(EXISTING_CAR_ID).brand("Update Ford").model("Update Focus").year(null).pilot(pilot)
                .build();

        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(carEntity));
        when(carRepository.save(carEntity)).thenReturn(updateCarEntity);

        CarDtoResponse response = carService.updateCar(EXISTING_CAR_ID, carDtoRequest);

        assertEquals(EXISTING_CAR_ID, response.getId());
        assertEquals(updateCarEntity.getBrand(), response.getBrand());
        assertEquals(updateCarEntity.getModel(), response.getModel());
    }
    @Test
    void testUpdateCarNonExisting() {
        CarDtoRequest carDtoRequest = CarDtoRequest.builder().model("Focus").brand("Ford").pilot(null).year(null)
                .build();
        when(carRepository.findById(NON_EXISTING_CAR_ID)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> carService.updateCar(NON_EXISTING_CAR_ID, carDtoRequest));
    }
    @Test
    void deleteCar() {
        CarEntity carEntity = CarEntity.builder().id(EXISTING_CAR_ID).brand("Ford").model("Focus").year(null).pilot(null)
                .build();
        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(Optional.of(carEntity));

        carService.deleteCar(EXISTING_CAR_ID);

        verify(carRepository).delete(carEntity);
    }
    @Test
    void testDeleteCarNonExisting() {
        when(carRepository.findById(NON_EXISTING_CAR_ID)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> carService.deleteCar(NON_EXISTING_CAR_ID));
    }
}