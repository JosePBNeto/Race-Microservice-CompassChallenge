package jose.mshistory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jose.mshistory.dtos.RaceDtoResponse;
import jose.mshistory.entities.Car;
import jose.mshistory.entities.Race;
import jose.mshistory.exceptions.IdNotFoundException;
import jose.mshistory.repositories.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryServiceImplTest {

    @Mock
    private HistoryRepository historyRepository;

    @Spy
    private ModelMapper modelMapper;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private HistoryServiceImpl historyService;

    @InjectMocks
    private HistoryServiceRabbitConsumer historyServiceRabbitConsumer;

    private static final String EXISTING_RACE_ID = "existingRaceId";
    private static final String NON_EXISTING_RACE_ID = "nonExistingRaceId";

    @Test
    public void testGetAllRaces() {
        List<Race> raceEntities = Stream.of(Race.builder()
                .track(null)
                .id(EXISTING_RACE_ID)
                .cars(null)
                .registerTimeStamp(null)
                .build()).collect(Collectors.toList());

        when(historyRepository.findAll()).thenReturn(raceEntities);

        List<RaceDtoResponse> raceDtoResponses = historyService.getAllRaces();

        assertNotNull(raceDtoResponses);
        assertEquals(raceEntities.size(), raceDtoResponses.size());
        assertEquals(EXISTING_RACE_ID, raceEntities.get(0).getId());

        verify(historyRepository, times(1)).findAll();
        verifyNoMoreInteractions(historyRepository);
    }

    @Test
    public void testGetRaceById() {
        Race race = Race.builder()
                .track(null)
                .id(EXISTING_RACE_ID)
                .cars(null)
                .registerTimeStamp(null)
                .build();


        when(historyRepository.findById(EXISTING_RACE_ID)).thenReturn(Optional.of(race));

        RaceDtoResponse raceDtoResponse = historyService.getRaceById(EXISTING_RACE_ID);

        assertNotNull(raceDtoResponse);

        assertEquals(EXISTING_RACE_ID, race.getId());
        verifyNoMoreInteractions(historyRepository);
    }

    @Test
    public void testGetRaceByIdNotFound() {

        when(historyRepository.findById(NON_EXISTING_RACE_ID)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> historyService.getRaceById(NON_EXISTING_RACE_ID));

    }

    @Test
    public void testSaveRaceResult() throws JsonProcessingException {
        String raceJsonString = "{'id': 1, 'name': 'Race 1'}";

        Race race = new Race();
        when(objectMapper.readValue(raceJsonString, Race.class)).thenReturn(race);

        historyServiceRabbitConsumer.saveRaceResult(raceJsonString);

        verify(historyRepository, times(1)).save(race);
    }
}