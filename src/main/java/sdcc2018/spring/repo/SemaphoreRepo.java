package sdcc2018.spring.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdcc2018.spring.domain.IntersectionGUI;
import sdcc2018.spring.domain.SemaphoreGUI;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SemaphoreRepo extends MongoRepository<SemaphoreGUI, String> {


}
