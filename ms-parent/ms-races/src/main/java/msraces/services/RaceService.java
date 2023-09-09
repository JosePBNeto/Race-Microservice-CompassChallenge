package msraces.services;

import msraces.dtos.Cars;

import java.util.List;
public interface RaceService {

    List<Cars> getCars();

    List<Cars> startRace();

    List<Cars> overtake(int position);
}
