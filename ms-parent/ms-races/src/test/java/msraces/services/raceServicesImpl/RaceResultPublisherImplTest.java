package msraces.services.raceServicesImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msraces.entities.Race;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RaceResultPublisherImplTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RaceResultPublisherImpl raceResultPublisher;


    @Test
    void testPublishRaceResult() throws JsonProcessingException {
        Race race = Race.builder()
                .id("1")
                .cars(null)
                .track(null)
                .build();

        String raceJsonString = "{'id': 1, 'cars': null,'track': null }";

        when(objectMapper.writeValueAsString(race)).thenReturn(raceJsonString);

        raceResultPublisher.publishRaceResult(race);

        verify(objectMapper, times(1)).writeValueAsString(race);
        verify(rabbitTemplate, times(1)).convertAndSend("race-result-queue", raceJsonString);
    }


}