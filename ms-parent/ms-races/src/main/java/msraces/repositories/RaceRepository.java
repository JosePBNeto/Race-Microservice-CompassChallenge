package msraces.repositories;

import msraces.entities.Race;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RaceRepository extends MongoRepository<Race, String> {
}
