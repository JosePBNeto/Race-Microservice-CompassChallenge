package jose.mshistory.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jose.mshistory.entities.Race;
import jose.mshistory.repositories.HistoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HistoryServiceRabbitConsumer {
    private HistoryRepository historyRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public HistoryServiceRabbitConsumer(HistoryRepository historyRepository, ObjectMapper objectMapper) {
        this.historyRepository = historyRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "race-result-queue")
    public void saveRaceResult(@Payload String raceJsonString){
        Race race = null;
        try {
            race = objectMapper.readValue(raceJsonString, Race.class);
            race.setRegisterTimeStamp(LocalDateTime.now());
            historyRepository.save(race);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
