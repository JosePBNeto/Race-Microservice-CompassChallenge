package msraces.controllers;

import msraces.dtos.Cars;
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
    public ResponseEntity<List<Cars>> getCars() {
        List<Cars> cars = raceService.getCars();
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping("/startRace")
    public ResponseEntity<List<Cars>> simulateRace(){
        List<Cars> cars = raceService.startRace();
        return ResponseEntity.ok().body(cars);
    }

    @PostMapping("/overtake/{position}")
    public ResponseEntity<List<Cars>> overtakeCar(@PathVariable int position){
        List<Cars> raceUpdate = raceService.overtake(position - 1);
        return ResponseEntity.ok().body(raceUpdate);
    }




}
