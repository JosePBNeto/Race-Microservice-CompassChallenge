package mscars.controllers;

import mscars.dtos.CarDtoRequest;
import mscars.dtos.CarDtoResponse;
import mscars.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public ResponseEntity<CarDtoResponse> saveCar(@RequestBody CarDtoRequest carDtoRequest) {
        CarDtoResponse carResponse = carService.save(carDtoRequest);
        return new ResponseEntity<>(carResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CarDtoResponse>> getAllCars() {
        List<CarDtoResponse> allCars = carService.getAllCars();
        return ResponseEntity.ok().body(allCars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDtoResponse> carById(@PathVariable String id){
        CarDtoResponse carResponse = carService.findCarById(id);
        return ResponseEntity.ok().body(carResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDtoResponse> updateCar(@PathVariable String id, @RequestBody CarDtoRequest carDtoRequest){
        CarDtoResponse carDtoResponse = carService.updateCar(id, carDtoRequest);
        return new ResponseEntity<>(carDtoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable String id){
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
