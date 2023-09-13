package msraces.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import msraces.dtos.CarDtoResponse;
import msraces.dtos.RaceResultResponse;
import msraces.entities.Car;
import msraces.entities.Pilot;
import msraces.entities.Race;
import msraces.entities.Track;
import msraces.services.raceServices.RaceManagerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RaceController.class)
class RaceControllerTest {

    @MockBean
    private RaceManagerService raceManagerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String EXISTING_CAR_ID = "existingCarId";
    private static final String NON_EXISTING_CAR_ID = "nonExistingCarId";

    @Test
    void testSimulateRace() throws Exception {
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


        when(raceManagerService.startRace(track)).thenReturn(cars);

        mockMvc.perform(post("/races/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cars)));

        verify(raceManagerService, times(1)).startRace(track);
        verifyNoMoreInteractions(raceManagerService);

    }


    @Test
    void testFinish() throws Exception {
        Track track = Track.builder()
                .name("Interlagos")
                .country("Brazil")
                .date(null)
                .build();
        Pilot pilot = Pilot.builder()
                .name("Jose")
                .age("27")
                .build();
        List<CarDtoResponse> carDtoResponse = List.of(
                CarDtoResponse.builder()
                        .pilot(pilot)
                        .finishPosition(1)
                        .model("Q3")
                        .brand("Audi")
                        .build(),
                CarDtoResponse.builder()
                        .pilot(pilot)
                        .finishPosition(1)
                        .model("Fiat")
                        .brand("Uno")
                        .build(),
                CarDtoResponse.builder()
                        .pilot(pilot)
                        .finishPosition(1)
                        .model("A3")
                        .brand("Audi")
                        .build()
        );
        RaceResultResponse raceResultResponse = RaceResultResponse.builder()
                .track(track)
                .cars(carDtoResponse)
                .build();

        when(raceManagerService.finishRace()).thenReturn(raceResultResponse);

        mockMvc.perform(post("/races/finish"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(raceResultResponse)));

        verify(raceManagerService, times(1)).finishRace();
        verifyNoMoreInteractions(raceManagerService);
    }
}