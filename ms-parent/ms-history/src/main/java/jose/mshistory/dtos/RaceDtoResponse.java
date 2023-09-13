package jose.mshistory.dtos;

import jose.mshistory.entities.Car;
import jose.mshistory.entities.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class RaceDtoResponse {
    private String id;
    private Track track;
    private List<Car> cars;
    private LocalDateTime registerTimeStamp;
}
