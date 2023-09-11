package msraces.feingClient;

import msraces.entities.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-cars")
public interface MsCarClient {

    @GetMapping("/cars")
    List<Car> getAllCars();

}
