package jose.mshistory.services;

import jose.mshistory.dtos.RaceDtoResponse;
import jose.mshistory.entities.Race;
import jose.mshistory.repositories.HistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService{

    private HistoryRepository historyRepository;
    private ModelMapper modelMapper;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository, ModelMapper modelMapper) {
        this.historyRepository = historyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RaceDtoResponse> getAllRaces() {
        return historyRepository.findAll().stream()
                .map(this::mapToRaceDtoResponse)
                .toList();
    }

    @Override
    public RaceDtoResponse getRaceById(String id) {
        return historyRepository.findById(id)
                .map(this::mapToRaceDtoResponse)
                .orElseThrow(() -> new RuntimeException("eee")); // TODO: CUSTOM EXECPTION
    }

    public RaceDtoResponse mapToRaceDtoResponse(Race race){
        return modelMapper.map(race, RaceDtoResponse.class);
    }

}
