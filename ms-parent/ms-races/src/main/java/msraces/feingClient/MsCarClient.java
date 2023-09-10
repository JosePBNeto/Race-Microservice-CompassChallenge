package msraces.feingClient;

import msraces.entities.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "ms-cars", url = "http://localhost:8081/cars")
public interface MsCarClient {

    @GetMapping
    List<Car> getAllCars();

}
