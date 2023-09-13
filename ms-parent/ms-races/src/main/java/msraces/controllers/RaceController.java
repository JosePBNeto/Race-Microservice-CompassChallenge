package msraces.controllers;

import jakarta.validation.Valid;
import msraces.dtos.RaceResultResponse;
import msraces.entities.Car;
import msraces.entities.Track;
import msraces.services.raceServices.RaceManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/races")
public class RaceController {

    private final RaceManagerService raceManagerService;

    public RaceController(RaceManagerService raceManagerService) {
        this.raceManagerService = raceManagerService;
    }

    @PostMapping("/start")
    public ResponseEntity<List<Car>> simulateRace(@RequestBody @Valid Track track){
        List<Car> cars = raceManagerService.startRace(track);
        return ResponseEntity.ok().body(cars);
    }

    @PostMapping("/overtake/{position}")
    public ResponseEntity<List<Car>> overtakeCar(@PathVariable int position){
        List<Car> raceUpdate = raceManagerService.overtake(position - 1);
        return ResponseEntity.ok().body(raceUpdate);
    }

    @PostMapping("/finish")
    public ResponseEntity<RaceResultResponse> finish(){
        RaceResultResponse race = raceManagerService.finishRace();
        return new ResponseEntity<>(race, HttpStatus.CREATED);
    }






}
