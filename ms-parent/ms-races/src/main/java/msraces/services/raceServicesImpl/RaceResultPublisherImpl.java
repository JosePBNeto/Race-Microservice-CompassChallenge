package msraces.services.raceServicesImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msraces.entities.Race;
import msraces.services.raceServices.RaceResultPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceResultPublisherImpl implements RaceResultPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RaceResultPublisherImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishRaceResult(Race race) {
        try {
            String resultRaceString = objectMapper.writeValueAsString(race);

            rabbitTemplate.convertAndSend("race-result-queue", resultRaceString);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
