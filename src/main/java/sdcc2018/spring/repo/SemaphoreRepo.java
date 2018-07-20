package sdcc2018.spring.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdcc2018.spring.domain.Semaphore;

@Repository
public interface SemaphoreRepo extends MongoRepository<Semaphore, String> {
}
