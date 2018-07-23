package sdcc2018.spring.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdcc2018.spring.domain.IntersectionGUI;

import java.util.ArrayList;

@Repository
public interface IntersectionRepo extends MongoRepository<IntersectionGUI, String> {

    IntersectionGUI findByidIntersection(int id);
    ArrayList<IntersectionGUI> findAll();

}
