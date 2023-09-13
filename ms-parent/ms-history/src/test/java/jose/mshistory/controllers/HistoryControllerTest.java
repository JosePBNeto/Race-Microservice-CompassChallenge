package jose.mshistory.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jose.mshistory.dtos.RaceDtoResponse;
import jose.mshistory.services.HistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllRaces() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/races/history")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void testGetRaceById() throws Exception {
        String raceId = "asd34asd";
        RaceDtoResponse raceDtoResponse = RaceDtoResponse.builder()
                .track(null)
                .id(raceId)
                .cars(null)
                .registerTimeStamp(null)
                .build();

        when(historyService.getRaceById(raceId)).thenReturn(raceDtoResponse);

        mockMvc.perform(get("/races/history/{id}", raceId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(objectMapper.writeValueAsString(raceDtoResponse))); // Adjust this as per your response structure

        // Verify that historyService.getRaceById() was called once with the correct ID
        verify(historyService, times(1)).getRaceById(raceId);
        verifyNoMoreInteractions(historyService);
    }
}
