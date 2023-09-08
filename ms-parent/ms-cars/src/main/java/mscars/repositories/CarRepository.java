package mscars.repositories;

import mscars.entity.CarEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<CarEntity, String> {
}
