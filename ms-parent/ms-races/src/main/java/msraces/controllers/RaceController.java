package msraces.controllers;

import jakarta.validation.Valid;
import msraces.entities.Car;
import msraces.entities.Race;
import msraces.entities.Track;
import msraces.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/races")
public class RaceController {

    @Autowired
    private RaceService raceService;
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCars() {
        List<Car> cars = raceService.getCars();
        return ResponseEntity.ok().body(cars);
    }

    @PostMapping("/start")
    public ResponseEntity<List<Car>> simulateRace(@RequestBody @Valid Track track){
        List<Car> cars = raceService.startRace(track);
        return ResponseEntity.ok().body(cars);
    }

    @PostMapping("/overtake/{position}")
    public ResponseEntity<List<Car>> overtakeCar(@PathVariable int position){
        List<Car> raceUpdate = raceService.overtake(position - 1);
        return ResponseEntity.ok().body(raceUpdate);
    }

    @PostMapping("/finish")
    public ResponseEntity<Race> finish(){
        Race race = raceService.finishRace();
        return ResponseEntity.ok().body(race);
    }






}
