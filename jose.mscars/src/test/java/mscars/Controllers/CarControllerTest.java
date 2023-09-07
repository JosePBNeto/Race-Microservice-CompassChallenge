package mscars.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import mscars.dtos.CarDtoRequest;
import mscars.dtos.CarDtoResponse;
import mscars.entity.PilotEntity;
import mscars.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.yaml.snakeyaml.events.Event;


import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void saveCar() throws Exception {
        PilotEntity pilot = PilotEntity.builder().id(null).name("Jao").age("23")
                .build();
        CarDtoRequest carDtoRequest = CarDtoRequest.builder().model("Focus").brand("Ford").pilot(pilot).year(null)
                .build();
        CarDtoResponse carDtoResponse = CarDtoResponse.builder().id("a24dfasd").brand("Ford").model("Focus").year(null)
                .build();

        when(carService.save(any())).thenReturn(carDtoResponse);

        mockMvc.perform(post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carDtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json((objectMapper.writeValueAsString(carDtoResponse))));

    }

    @Test
    void getAllCars() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk());
    }

    @Test
    void getCArById() throws Exception {
        PilotEntity pilot = PilotEntity.builder().id(null).name("Jao").age("23")
                .build();
        CarDtoResponse carDtoResponse = CarDtoResponse.builder().id("a24dfasd").brand("Ford").model("Focus").year(null)
                .build();

        when(carService.findCarById("asds234234")).thenReturn(carDtoResponse);

        mockMvc.perform(get("/cars/asds234234"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(carDtoResponse)));

    }

    @Test
    void testCarByIdNotFound() throws Exception {

        when(carService.findCarById("2")).thenReturn(null);

        mockMvc.perform(get("/your-controller-path/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCar() throws Exception {
        String carIdToDelete = "asdasd123";

        doNothing().when(carService).deleteCar(carIdToDelete);

        mockMvc.perform(delete("/cars/" + carIdToDelete)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(carService).deleteCar(carIdToDelete);
    }

    @Test
    void updateCar() throws Exception {
        String carIdToUpdate = "asdasd";

        PilotEntity pilot = PilotEntity.builder().id(null).name("Jao").age("23")
                .build();
        CarDtoRequest carDtoRequest = CarDtoRequest.builder().model("Focus").brand("Ford").pilot(pilot).year(null)
                .build();
        CarDtoResponse carDtoResponse = CarDtoResponse.builder().id("a24dfasd").brand("Ford").model("Focus").year(null)
                .build();

        when(carService.updateCar(carIdToUpdate, carDtoRequest)).thenReturn(carDtoResponse);

        mockMvc.perform(put("/cars/" + carIdToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carDtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(carDtoResponse)));


    }



}