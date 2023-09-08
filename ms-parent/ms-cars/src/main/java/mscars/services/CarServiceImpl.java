package mscars.services;

import mscars.dtos.CarDtoRequest;
import mscars.dtos.CarDtoResponse;
import mscars.entity.CarEntity;
import mscars.exceptions.IdNotFoundException;
import mscars.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    private static final String IDNOTFOUND = "Car ID not found";

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarDtoResponse save(CarDtoRequest carDtoRequest) {
        CarEntity saved = carRepository.save(mapToCarEntity(carDtoRequest));
        return mapToCarDtoResponse(saved);
    }

    @Override
    public List<CarDtoResponse> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::mapToCarDtoResponse)
                .toList();
    }

    @Override
    public CarDtoResponse findCarById(String id) {
        return carRepository.findById(id).map(this::mapToCarDtoResponse)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));
    }

    @Override
    public CarDtoResponse updateCar(String id, CarDtoRequest carDtoRequest) {
        return carRepository.findById(id)
                .map(carEntity -> updateAndSaveCar(carEntity, carDtoRequest))
                .map(this::mapToCarDtoResponse)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));

    }

    @Override
    public void deleteCar(String id) {
        carRepository.findById(id)
                .ifPresentOrElse(carRepository::delete,
                        () -> {throw new IdNotFoundException(IDNOTFOUND);
                });
    }


    public CarDtoResponse mapToCarDtoResponse(CarEntity carEntity){
        return modelMapper.map(carEntity, CarDtoResponse.class);
    }
    public CarEntity mapToCarEntity (CarDtoRequest carDtoRequest){
        return modelMapper.map(carDtoRequest, CarEntity.class);
    }

    public CarEntity updateAndSaveCar(CarEntity carEntityToUpdate, CarDtoRequest carDtoRequestUpdated){
        modelMapper.map(carDtoRequestUpdated, carEntityToUpdate);
        return carRepository.save(carEntityToUpdate);

    }

}
