package jose.mshistory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jose.mshistory.entities.Race;
import jose.mshistory.repositories.HistoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    private final RabbitTemplate rabbitTemplate;
    private HistoryRepository historyRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public HistoryService(RabbitTemplate rabbitTemplate, HistoryRepository historyRepository, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.historyRepository = historyRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "race-result-queue")
    public void saveRaceResult(@Payload String raceJsonString){
        Race race = null;
        try {
            race = objectMapper.readValue(raceJsonString, Race.class);
            System.out.println("Result received = " + race + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            historyRepository.save(race);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
