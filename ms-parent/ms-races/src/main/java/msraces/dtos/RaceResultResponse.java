package msraces.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import msraces.entities.Track;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaceResultResponse {
    private Track track;
    private List<CarDtoResponse> cars;
}
