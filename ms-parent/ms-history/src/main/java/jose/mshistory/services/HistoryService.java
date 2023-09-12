package jose.mshistory.services;

import jose.mshistory.dtos.RaceDtoResponse;

import java.util.List;

public interface HistoryService {
    public List<RaceDtoResponse> getAllRaces();
    public RaceDtoResponse getRaceById();
}
