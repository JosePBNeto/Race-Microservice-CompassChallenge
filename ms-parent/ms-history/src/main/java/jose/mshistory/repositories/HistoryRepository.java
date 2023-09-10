package jose.mshistory.repositories;

import jose.mshistory.entities.Race;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<Race, String> {
}
