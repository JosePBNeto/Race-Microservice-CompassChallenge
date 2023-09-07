package mscars.services;

import mscars.dtos.CarDtoRequest;
import mscars.dtos.CarDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {

    CarDtoResponse save(CarDtoRequest carDtoRequest);

    List<CarDtoResponse> getAllCars();

    CarDtoResponse findCarById(String id);

    CarDtoResponse updateCar(String id, CarDtoRequest carDtoRequest);

    void deleteCar(String id);
}
